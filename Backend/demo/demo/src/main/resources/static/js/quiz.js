
// --- Global Variables ---
let questions = [];
let currentIndex = 0;
let userAnswers = {}; // Format: { questionIndex: "optionValue" }
let statusArray = []; // Format: 'not-visited', 'attempted', 'not-attempted', 'review'
let timerInterval;
let timeLeft = 600; // 10 Minutes in seconds

// --- 1. Initialization ---
document.addEventListener("DOMContentLoaded", () => {
    fetchQuestions();
});

function fetchQuestions() {
    fetch('/api/quiz/all')
        .then(res => res.json())
        .then(data => {
            questions = data;
            console.log('Questions loaded:', questions.length);
            // Initialize all questions as 'not-visited'
            statusArray = new Array(questions.length).fill('not-visited');
            renderQuestion();
            renderPalette();
            startTimer();
        })
        .catch(err => console.error("Error fetching questions:", err));
}

// --- 2. Rendering Logic ---
function renderQuestion() {
    if (questions.length === 0) return;

    const q = questions[currentIndex];
    document.getElementById('q-number').innerText = `Question ${currentIndex + 1}`;
    document.getElementById('q-text').innerText = q.questionTitle;
    document.getElementById('l1').innerText = q.option1;
    document.getElementById('l2').innerText = q.option2;
    document.getElementById('l3').innerText = q.option3;
    document.getElementById('l4').innerText = q.option4;

    // Reset radio buttons
    const radios = document.querySelectorAll('input[name="answer"]');
    radios.forEach(r => r.checked = false);

    // If user already answered this, pre-select it
    if (userAnswers[currentIndex]) {
        const savedOption = userAnswers[currentIndex];
        document.querySelector(`input[value="${savedOption}"]`).checked = true;
    }
}

function renderPalette() {
    const container = document.getElementById('num-grid');
    container.innerHTML = '';

    statusArray.forEach((status, index) => {
        const btn = document.createElement('div');
        btn.innerText = index + 1;
        // Apply CSS classes: num-circle is base, status is dynamic (attempted, review, etc.)
        btn.className = `num-circle ${status}`;
        
        // Jump to question on click
        btn.onclick = () => {
            currentIndex = index;
            renderQuestion();
        };
        container.appendChild(btn);
    });
}

// --- 3. Navigation & Actions ---

function saveAndNext() {
    const selected = document.querySelector('input[name="answer"]:checked');
    
    if (selected) {
        userAnswers[currentIndex] = selected.value;
        statusArray[currentIndex] = 'attempted'; // Green
    } else {
        statusArray[currentIndex] = 'not-attempted'; // Red
    }
    
    moveNext();
}

function markForReview() {
    const selected = document.querySelector('input[name="answer"]:checked');
    if (selected) {
        userAnswers[currentIndex] = selected.value;
    }
    statusArray[currentIndex] = 'review'; // Purple
    moveNext();
}

function previousQ() {
    if (currentIndex > 0) {
        currentIndex--;
        renderQuestion();
        renderPalette();
    }
}

function moveNext() {
    if (currentIndex < questions.length - 1) {
        currentIndex++;
        renderQuestion();
        renderPalette();
    } else {
        renderPalette();
    }
}

// --- 4. Timer & Submission ---

function startTimer() {
    timerInterval = setInterval(() => {
        let mins = Math.floor(timeLeft / 60);
        let secs = timeLeft % 60;
        document.getElementById('timer').innerText = 
            `Time Left: ${mins}:${secs < 10 ? '0' + secs : secs}`;

        if (timeLeft <= 0) {
            clearInterval(timerInterval);
            submitQuiz();
        }
        timeLeft--;
    }, 1000);
}

function submitQuiz() {
    if(!confirm("Are you sure you want to submit?")) return;

    console.log('Submitting quiz...');
    console.log('User Answers:', userAnswers);
    console.log('Questions:', questions);

    // Map the local state to the Backend DTO (Response.java)
    const submissionData = Object.keys(userAnswers).map(index => {
        const q = questions[index];
        const optionNum = userAnswers[index];
        
        // Map option number (1,2,3,4) to actual option text
        let optionText = '';
        if (optionNum === '1') optionText = q.option1;
        else if (optionNum === '2') optionText = q.option2;
        else if (optionNum === '3') optionText = q.option3;
        else if (optionNum === '4') optionText = q.option4;
        
        console.log('Question:', q.questionTitle, 'Selected:', optionText, 'Correct:', q.rightAnswer);
        
        return {
            id: q.id,
            response: optionText
        };
    });

    console.log('Submission Data:', submissionData);

    fetch('/api/quiz/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(submissionData)
    })
    .then(res => {
        console.log('Response status:', res.status);
        return res.json();
    })
    .then(score => {
        console.log('Score received:', score);
        window.location.href = `/result?score=${score}`;
    })
    .catch(err => {
        console.error('Submission error:', err);
        alert("Submission failed. Check Console.");
    });
}