@Entity
@Data // If using Lombok, otherwise generate Getters/Setters
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    
    // Marking Scheme
    private int positiveMarks = 4;
    private int negativeMarks = 1;
}
