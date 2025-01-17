package club.esprit.backend.services.quiz;

import club.esprit.backend.entities.quiz.*;

import java.util.List;

public interface ITestService {
    public String addTest(Test test);

    public void activateanactivate(Long testId);

    public void deletetest(Long testId);

    public String modifyTest(Long id, String title, String description);

    public List<Test> retrieveAllTests();

    public Test retrieveTest(Long testId);

    public String addquestiontotest(Long idTest, Question question);

    public void deletequestion(Long questionId);

    public Test importquiz(Quizimport quiz, String imgnbr);

    //
//
//
//
//
    public void addusertest(UserTest userTest);

    public List<UserTest> showalltests();

    //
    //
    public Test addtestwithapi(List<ApiOpenquizzdb> apiOpenquizzdbs);

    public void deleteut(Long id);

    //
    public void addgemini(Gemini gemini);

    public List<Gemini> getallgemini(Long id);

    // test's comments service
    public List<TestComments> getComments(Long id);
    public List<TestComments> getCommentsThatNeedToBeAnnsered();

    public TestComments createComment(TestComments testComments);

    public TestComments updateComment(String text, String parentId,Long id);

    public void deleteComment(Long parentId);
// ranking of the results
public List<UserTest> getTop3UsersPerTest();

}
