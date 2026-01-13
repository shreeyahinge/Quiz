@Controller
public class ResultController {

    @GetMapping("/result")
    public String showResult(@RequestParam int score, Model model) {
        model.addAttribute("totalScore", score);
        return "result"; // Looks for result.html in templates
    }
}
