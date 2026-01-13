@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    QuestionRepository repo;

    @PostMapping("/addQuestion")
    public String addQuestion(@ModelAttribute Question question) {
        repo.save(question);
        return "redirect:/teacher/dashboard"; // Redirect back after saving
    }
}
