package club.esprit.backend.services.quiz;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.quiz.*;
import club.esprit.backend.repository.ClubRepository;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.repository.quiz.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestServiceImp implements ITestService {
    TestRepository testRepository;
    QuestionRepository questionRepository;
    QuestionOptionRepository questionOptionRepository;
    GeminiRepository geminiRepository;
    TestCommentsRepository testCommentsRepository;

    UserRepository userRepository;
    UserTestRepository userTestRepository;
    ClubRepository clubRepository;
    /**
     * @param test
     * @return
     */
    @Override
    public String addTest(Test test) {
        Set<Question> questions = new HashSet<>();
        for (Question q : test.getQuestions()) {
            questions.add(q);
            Set<QuestionOption> options = new HashSet<>();
            for (QuestionOption o : q.getQuestionOptions())
                options.add(o);
            q.setQuestionOptions(options);
        }
        test.setActive(true);
        test.setQuestions(questions);
        testRepository.save(test);

        return "test added succesfuly";
    }

    /**
     * @param testId
     */
    @Override
    public void activateanactivate(Long testId) {
        Test test = testRepository.findById(testId).orElse(null);
        if (!test.isActive())
            test.setActive(true);
        else
            test.setActive(false);
        testRepository.save(test);
    }

    @Transactional
    public void deletetest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));
        List<Club> clubs = clubRepository.findAll();
        for (Club club : clubs) {
            List<Test> tests = club.getTests();
            if (tests.contains(test)) {
                tests.remove(test);
                club.setTests(tests);
                clubRepository.save(club);
            }
        }
        testRepository.deleteById(testId);
    }

    /**
     * @return
     */
    @Override
    public String modifyTest(Long id, String title, String description) {
        Test oldtest = testRepository.findById(id).orElse(null);
        oldtest.setTitle(title);
        oldtest.setDescription(description);
        //oldtest.setImage(test.getImage());
        testRepository.save(oldtest);
        return "test updated succesfuly";
    }

    /**
     * @return
     */
    @Override
    public List<Test> retrieveAllTests() {
        return testRepository.findAll();
    }

    /**
     * @param testId
     * @return
     */
    @Override
    public Test retrieveTest(Long testId) {
        return testRepository.findById(testId).orElse(null);
    }

    /**
     * @param idTest
     * @param question
     * @return
     */
    @Override
    public String addquestiontotest(Long idTest, Question question) {
        Test test = testRepository.findById(idTest).orElse(null);
        question.setImage(question.getQuestion());
        test.getQuestions().add(question);
        testRepository.save(test);
        return null;
    }

    /**
     * @param questionId
     */
    @Override
    @Transactional
    public void deletequestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        List<Test> tests = testRepository.findAllByQuestionId(questionId);
        for (Test test : tests) {
            Set<Question> newquestions = new HashSet<>();
            for (Question q : test.getQuestions())
                if (q.getId() != questionId)
                    newquestions.add(q);
            test.setQuestions(newquestions);
            testRepository.save(test);
        }
        assert question != null;
        questionOptionRepository.deleteAll(question.getQuestionOptions());
        questionRepository.deleteById(questionId);
    }

    @Override
    public Test importquiz(Quizimport quiz, String imgnbr) {
        QQuizimport[] qQuizimport = quiz.getQuizz();
        Test test = new Test();
        test.setTitle(quiz.getNom());

        Set<Question> questions = new HashSet<>();
        for (QQuizimport q : qQuizimport) {
            Question question = new Question();
            question.setQuestion(q.getQuestion());
            question.setImage(q.getQuestion());
            question.setAnecdote(q.getAnecdote());
            Set<QuestionOption> options = new HashSet<>();
            String[] propositions = q.getPropositions();
            String ans = q.getRéponse();
            for (String proposition : propositions) {
                QuestionOption option = new QuestionOption();
                option.setAnswer(proposition);
                if (option.getAnswer().equals(ans)) {
                    option.setIscorrect(true);
                } else {
                    option.setIscorrect(false);
                }
                options.add(option);
            }

            question.setQuestionOptions(options);
            questions.add(question);
        }
        test.setImage(imgnbr);
        test.setActive(true);
        test.setQuestions(questions);
        return testRepository.save(test);
    }


    @Override
    public void addusertest(UserTest userTest) {
        User user = userTest.getUser();
        Test test = userTest.getTest();
        userTest.setUser(user);
        userTest.setTest(test);
        userTest.setDate(new Date());
        userTestRepository.save(userTest);
    }

    @Override
    public List<UserTest> showalltests() {
        return userTestRepository.findAll();
    }

    @Override
    public Test addtestwithapi(List<ApiOpenquizzdb> apiOpenquizzdbs) {
        Test test = new Test();
        System.out.println(apiOpenquizzdbs);
        ApiOpenquizzdb anyone = apiOpenquizzdbs.get(0);
        test.setTitle(anyone.getCategorie());
        test.setDescription("a simple test about " + anyone.getCategorie() + " in" + anyone.getLangue() + " and the difficulti is : " + anyone.getDifficulte());
        test.setImage("assets/img/quiz/" + anyone.getCategorie() + ".png");
        Set<Question> questions = new HashSet<>();
        for (ApiOpenquizzdb q : apiOpenquizzdbs) {
            Question question = new Question();
            question.setQuestion(q.getQuestion());
            question.setImage(q.getTheme());
            Set<QuestionOption> options = new HashSet<>();
            String[] propositions = q.getAutres_choix();
            String ans = q.getReponse_correcte();
            for (String proposition : propositions) {
                QuestionOption option = new QuestionOption();
                option.setAnswer(proposition);
                if (option.getAnswer().equals(ans)) {
                    option.setIscorrect(true);
                } else {
                    option.setIscorrect(false);
                }
                options.add(option);
            }

            question.setQuestionOptions(options);
            questions.add(question);
            question.setAnecdote(q.getAnecdote());
            question.setWikipedia(q.getWikipedia());
        }
        test.setActive(true);
        test.setQuestions(questions);
        testRepository.save(test);
 return test;
    }

    @Override
    public void deleteut(Long id) {
        userTestRepository.deleteById(id);
    }

    @Override
    public void addgemini(Gemini gemini) {
        gemini.setDate(new Date());
        geminiRepository.save(gemini);
    }

    @Override
    public List<Gemini> getallgemini(Long id) {
        List<Gemini> all = geminiRepository.findAll();
        List<Gemini> finallist = new ArrayList<>();
        for (Gemini g : all)
            if (g.getIduser() == id)
                finallist.add(g);
        return finallist;
    }


    // the comments of the test
    @Override
    public List<TestComments> getComments(Long id) {

        // Retrieve all comments from the repository
        List<TestComments> allComments = testCommentsRepository.findAll();
        List<TestComments> modifiedlist=new ArrayList<>();
        for(TestComments t :allComments){
            //t.setUserimage(userRepository.findById(Long.parseLong(t.getUserId())).orElse(null).getImage());
            t.setUsername(userRepository.findById(Long.parseLong(t.getUserId())).orElse(null).getName());
            modifiedlist.add(t);
        }
        // Filter the comments based on the provided questionId
        return modifiedlist.stream()
                .filter(comment -> Objects.equals(comment.getQuestionId(), id.toString()))
                .collect(Collectors.toList());

    }

    @Override
    public List<TestComments> getCommentsThatNeedToBeAnnsered() {
        List<TestComments> alllist=testCommentsRepository.findAll();
        List<TestComments> filtredlist=new ArrayList<>();
        for (TestComments c :alllist){
            if (Objects.equals(c.getResponse(), "Not yet ansered ..."))
                filtredlist.add(c);
        }
        return filtredlist;
    }

    @Override
    public TestComments createComment(TestComments testComments) {
        testComments.setCreatedAt(new Date());
        testComments.setResponse("Not yet ansered ...");

        //TestComments testComments=new TestComments();
        //testComments.setBody(text);
        //String id=testComments.getParentId();
        //testComments.setParentId(partnerId);
        //System.out.println(testComments);
        return testCommentsRepository.save(testComments);
    }

    @Override
    public TestComments updateComment(String text, String  parentId,Long id) {
        TestComments testComments = testCommentsRepository.findByQuestionIdAndId(parentId,id);
        testComments.setResponse(text);
        testComments.setRepliedAt(new Date());
        return testCommentsRepository.save(testComments);
    }

    @Override
    public void deleteComment(Long parentId) {
        testCommentsRepository.deleteById(parentId);
    }

    @Override
    public List<UserTest> getTop3UsersPerTest() {
        List<UserTest> topUsers = new ArrayList<>();

        // Retrieve all tests
        List<Test> tests = testRepository.findAll();

        for (Test test : tests) {
            // Retrieve UserTests for the current test
            List<UserTest> userTests = userTestRepository.findByTestIdOrderByScoreDesc(test.getId());

            // Take the top 3 UserTests
            if (!userTests.isEmpty()) {
                int limit = Math.min(userTests.size(), 3);
                topUsers.addAll(userTests.subList(0, limit));
            }
        }

        return topUsers;
    }
}
