@Service
public class QuizService {
    @Autowired
    QuestionRepository questionRepository;

    public int calculateResult(List<Response> responses) {
        int score = 0;
        for (Response res : responses) {
            // Find the original question in the DB using the ID from the student's response
            Optional<Question> qOptional = questionRepository.findById(res.getId());
            
            if (qOptional.isPresent()) {
                Question question = qOptional.get();
                // Compare student answer with the correct answer from DB
                if (res.getResponse().equals(question.getRightAnswer())) {
                    score += 4; // Positive marking
                } else {
                    score -= 1; // Negative marking
                }
            }
        }
        return score;
    }
}