@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {

    @Autowired
    QuizService quizService;

    @PostMapping("/submit")
    public int submitQuiz(@RequestBody List<Response> responses) {
        // This takes the list from JS and returns the final integer score
        return quizService.calculateResult(responses);
    }
}