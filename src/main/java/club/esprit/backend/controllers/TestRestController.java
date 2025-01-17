package club.esprit.backend.controllers;

import club.esprit.backend.entities.quiz.*;
import club.esprit.backend.services.quiz.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/quiz")
public class TestRestController {
    @Autowired
    ITestService testService;
    @PostMapping("/add-test")
    public String addTest(@RequestBody Test test) {
        return testService.addTest(test);
    }

    @PostMapping("/add-quiz/{img}")
    public Test importquiz(@RequestBody Quizimport quiz,@PathVariable("img") String img) {
        //https://openquizzdb.org/media/cover/oqdb_quizz_204.jpg
        String image="https://openquizzdb.org/media/cover/oqdb_quizz_"+img+".jpg";
        return testService.importquiz(quiz,image);
    }

    @GetMapping("/retrieve-all-tests")
    public List<Test> retrieveAllTests() {
        return testService.retrieveAllTests();
    }

    @GetMapping("/retrieve-test/{id}")
    public Test retrieveTest(@PathVariable ("id") Long id) {
        return testService.retrieveTest(id);
    }



    @PostMapping("/pass-test")
    public void addusertest(@RequestBody UserTest userTest){
        testService.addusertest(userTest);
    }

    @GetMapping("/retrieve-all-passed")
    public List<UserTest> showalltests(){return testService.showalltests();}

    @PutMapping("/activateanactivate/{testId}")
    public void activateanactivate(@PathVariable("testId") Long testId) {
        testService.activateanactivate(testId);
    }

    @PostMapping("/add-quiz-api")
    public Test addtestwithapi(@RequestBody List<ApiOpenquizzdb> apiOpenquizzdbs) {
        return testService.addtestwithapi(apiOpenquizzdbs);
    }

    @DeleteMapping("/delete/{testId}")
    public void deletetest(@PathVariable("testId") Long testId) {
        testService.deletetest(testId);
    }

    @PutMapping("/updatetest/{id}/{title}/{description}")
    public String modifyTest(@PathVariable("id") Long id,@PathVariable("title") String title,@PathVariable("description") String description){
        return testService.modifyTest(id,title,description);
    }

    @DeleteMapping("/deletequestion/{id}")
    public void deletequestion(@PathVariable("id") Long id) {
        testService.deletequestion(id);
    }

    @PostMapping("/add-question/{id}")
    public String addquestiontotest(@PathVariable("id") Long id, @RequestBody Question question){
        return testService.addquestiontotest(id,question);
    }

    @DeleteMapping("/deletehistory/{id}")
    public void deleteut(@PathVariable("id") Long id) {
        testService.deleteut(id);
    }


    //gemini
    @PostMapping("/add-gemini")
    public void addgemini(@RequestBody Gemini gemini){
        testService.addgemini(gemini);
    }

    @GetMapping("/retrieve-all-gemini/{id}")
    public List<Gemini> getallgemini(@PathVariable("id") Long id) {
        return testService.getallgemini(id);
    }





    // the comments of the test (not yet used)
    @GetMapping("/retrieve-all-TestComments/{id}")
    public List<TestComments> getComments(@PathVariable("id") Long id) {
        return testService.getComments(id);
    }
// get the non replyed comments
    @GetMapping("/retrieve-non-replyedComments")
    public List<TestComments> getCommentsThatNeedToBeAnnsered() {
        return testService.getCommentsThatNeedToBeAnnsered();
    }


    @PostMapping("/createComment")
    public TestComments createComment(@RequestBody TestComments testComments) {
        return testService.createComment(testComments);
    }

    @PutMapping("/updateComment/{t}/{id}/{idd}")
    public void updateComment(@PathVariable("t") String t,@PathVariable("id") String id,@PathVariable("idd") Long idd) {
        testService.updateComment(t,id,idd);
    }

    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        testService.deleteComment(id);
    }


    @GetMapping("/retrieve-rank")
    public List<UserTest> getTop3UsersPerTest(){return testService.getTop3UsersPerTest();}
}
