package com.example.logic.learningeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
// this class let us choose a course
// if we sign in for the first time and we choose a course ,then an intro test appear to decide our level, and according to our level a list of lectures appear
// if we already are nigned up then a list of lectures appear according to our level saved in database
public class ChooseCourse extends AppCompatActivity {
    DatabaseHandler database;
    TextView courseContentView;
    Spinner spinner;
    ArrayAdapter<String> adapter ;
    Button btn;
    List<Courses> coursesList;
    GlobalVar globalVar;
    int studentId;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);
        globalVar = (GlobalVar)getApplication();
        int check = globalVar.getNum();

        database = new DatabaseHandler(this);
        courseContentView = (TextView)findViewById(R.id.courseContent);
        String userName = globalVar.getStudentName();
        TextView textView = (TextView) findViewById(R.id.wlcmtext);
        textView.setText("Welcome, " + userName);
        studentId = globalVar.getStudentId();

        if (check==0) {
            fillDatabase();
        }globalVar.setNum(1);

        spinner = (Spinner) findViewById(R.id.coursesSpinner);
        adapter = new ArrayAdapter<String>(ChooseCourse.this, android.R.layout.simple_spinner_dropdown_item, takeCourseList());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String itemName = (String) spinner.getSelectedItem();
                updateCourseContent(itemName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        btn = (Button) findViewById(R.id.ChosCoursebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemName = (String) spinner.getSelectedItem();

                courseId = database.getCourseId(itemName);

                String checkEnrollLevel = database.checkEnroll(studentId,courseId);
                if (checkEnrollLevel.equals("not found")){

                    Intent intent = new Intent(ChooseCourse.this , TestActivity.class);
                    intent.putExtra("testName","Intro Test Grammar");

                    startActivity(intent);
                }else {
                    globalVar.setLevel(checkEnrollLevel);
                    Intent intent = new Intent(ChooseCourse.this, LecturesList.class);
                    startActivity(intent);

                }

                globalVar.setCourseId(courseId);
                globalVar.setCourseName(itemName);

            }
        });


    }

    private ArrayList takeCourseList() {
        coursesList = database.getAllCourses();
        ArrayList<String> courseNameList = new ArrayList<String>();
        for (Courses c : coursesList) {
            String courseName = "" + c.getCourse_name();
            courseNameList.add(courseName);
        }
        return courseNameList;
    }

    void updateCourseContent(String courseName) {
        coursesList = database.getAllCourses();
        for (Courses c : coursesList) {
            if (c.getCourse_name().equals(courseName)) {
                String courseContent = "" + c.getCourse_describe();
                courseContentView.setText(courseContent);
            }
        }
    }



void fillDatabase(){

    database.addCourses(new Courses(
            "English Grammar", "you will learn about the Grammar and the structure of English language. "));

    //beginner level lectures

    database.addLecture(new Lectures(
            "Sentence Structure","This English grammar lesson on word order will teach you about the sentence structure of phrases in the English language.",
            "VPyo8-Pr55Q","beginner","English Grammar"));


    database.addLecture(new Lectures(
            "Position of Adverbs","This English lesson on word order will teach you about the position of adverbs in an English sentence.",
            "3MVynXr3Yt8","beginner","English Grammar"));


    database.addLecture(new Lectures(
            "Present Simple vs Present Continuous","In this English speaking lesson you will find easy and clear explanations of the present simple and present continuous.",
            "AEBRIBtq7q0","beginner","English Grammar"));


    database.addLecture(new Lectures(
            "Questions","This English lesson takes you through the formulation and usage of questions in English.",
            "kjR-3_ctBMA","beginner","English Grammar"));

    database.addLecture(new Lectures(
            "Negatives","English lesson on forming negatives in the English Language.",
            "ayn_9JOK_i8","beginner","English Grammar"));

//intermediate level lectures

    database.addLecture(new Lectures(
            "English Modal Verbs | Can - Could - May - Might",
            "In this English lesson,you will learn how to use the Modal Verbs can, could, may and might.",
            "SaBH_huiJSM","intermediate","English Grammar"));

    database.addLecture(new Lectures(
            "Should - Must - Have to | English Modal Verbs",
            "In This English lesson, we will be looking at the differences between 'should', 'must' and 'have to'.",
            "kZ3rHYcWFlA","intermediate","English Grammar"));

    database.addLecture(new Lectures(
            "Present Perfect vs Past Simple",
            "In this English lesson you will find easy and clear explanations of when and how to use the present perfect (I have been doing) and past simple (I did).",
            "eA2_i2yDaAo","intermediate","English Grammar"));

    database.addLecture(new Lectures(
            "Future Continuous vs Future Perfect vs Future Perfect Continuous",
            "You will learn how and when to use the Future Continuous, Future Perfect and Future Perfect Continuous when speaking English.",
            "48kdsro9rzM","intermediate","English Grammar"));

    database.addLecture(new Lectures(
            "Action Verbs vs State Verbs",
            "In this video lesson you will learn when and how to use state verbs in the English language.",
            "i2MbvSq0BLU","intermediate","English Grammar"));

//advanced level lectures

    database.addLecture(new Lectures(
            "All Tenses",
            "In this lesson you will learn about all the tenses in English and compare them and know what is the difference between each of them",
            "84jVz0D-KkY","advanced","English Grammar"));

    database.addLecture(new Lectures(
            "Question Tags",
            "In this lesson you wil learn about the question tags, which are small question we add at the end of a sentence ",
            "IU9izSK_ArM","advanced","English Grammar"));

    database.addLecture(new Lectures(
            "Conditionals",
            "In this English grammar lesson you will learn all about how to use conditionals in the English language.",
            "h-Np7dmvw0U","advanced","English Grammar"));

    database.addLecture(new Lectures(
            "Passive Voice",
            "In this English lesson, we will be looking at how to formulate and use the Passive Voice.",
            "pxbQ2U3Uuv0","advanced","English Grammar"));

    database.addLecture(new Lectures(
            "Indirect Speech",
            "we will be looking at how we report statements, and the changes we must make to the original sentence.",
            "Vwlm-GoPzJI","advanced","English Grammar"));

    //intro test for grammar course
    // to determine the level of the student
    database.addTests(new Tests(
            "Intro Test Grammar",
            "this test will evaluate your level in English Grammar","Grammar intro" ));

    database.addQuestion(new Questions(
            "what is the subject in a sentence?","who does an action","who receives an action","neither","who does an action","Intro Test Grammar"));
    database.addQuestion(new Questions(
            "what is the object in a sentence?","who does an action","who receives an action","neither","who receives an action","Intro Test Grammar"));
    database.addQuestion(new Questions(
            "what is the correct time expression for the past tense?","tomorrow","yesterday","now","yesterday","Intro Test Grammar"));
    database.addQuestion(new Questions(
            "which is a correct sentence?","I am study English","I studying English","I am studying English","I am studying English","Intro Test Grammar"));
    database.addQuestion(new Questions(
            "complete the sentence: I read books ______ day.","every","now","last","every","Intro Test Grammar"));
    database.addQuestion(new Questions(
            "complete the sentence: She's been reading since ______.","3 hours","at 3 pm","3 am","3 am","Intro Test Grammar"));
    database.addQuestion(new Questions("Choose the correct verb:I am a teacher and I _______ for 3 years ","have teach","have taught","taught","have taught","Intro Test Grammar"));
    database.addQuestion(new Questions("Complete: We are not leaving,______?","aren't we","we are","are we","are we","Intro Test Grammar"));
    database.addQuestion(new Questions("Complete: If you leave the ice outside, it ______.",
            "will melts","melts","will melt","melts","Intro Test Grammar"));
    database.addQuestion(new Questions("Choose the correct sentence:",
            "a book has written","a book has been written","a book has been wrote","a book has been written","Intro Test Grammar"));
    database.addQuestion(new Questions("Change to indirect speech:He said: I didn't go to school.",
            "I hadn't gone to school","He hadn't gone to school","He hasn't gone to school","He hadn't gone to school","Intro Test Grammar"));
    database.addQuestion(new Questions("I _____ English now","can spoke","could speak","can speak","can speak","Intro Test Grammar"));
    database.addQuestion(new Questions("Complete the sentence: We are studying ______",
            "at the moment","sometimes","never" ,"at the moment","Intro Test Grammar"));
    database.addQuestion(new Questions("Complete the sentence:Has she ______ the book?",
            "written","wrote","write","written","Intro Test Grammar"));
    database.addQuestion(new Questions( "Choose the correct sentence:",
            "We don't had a class.","We don't has a class.","We don't have a class.","We don't have a class.","Intro Test Grammar"));

    //beginner level tests
    database.addTests(new Tests(
            "Test 1.1.1",
            "this test will evaluate your level in English Grammar","Sentence Structure" ));

    database.addQuestion(new Questions(
            "Choose the correct order:","Students books read","Read books students","Students read books","Students read books","Test 1.1.1"));
    database.addQuestion(new Questions(
            "Which word describes the action in a sentence?","subject","object","verb","verb","Test 1.1.1"));
    database.addQuestion(new Questions(
            "Choose the correct order:,","Children to school go in the morning" ,"To school go children in the morning","Children go to school in the morning","Children go to school in the morning","Test 1.1.1"));
    database.addQuestion(new Questions(
            "Which word describes the time expression?","when","who","where","when","Test 1.1.1"));
    database.addQuestion(new Questions(
            "Choose the correct order:", "I go to work in the evening","to work I go in the evening","in the evening I go to work","I go to work in the evening","Test 1.1.1"));
    database.addQuestion(new Questions(
            "Which word describes the place?","when","who","where","where","Test 1.1.1"));


    database.addTests(new Tests(
            "Test 1.1.2",
            "this test will evaluate your level in English Grammar","Position of Adverbs" ));

    database.addQuestion(new Questions(
            "Choose the correct sentence:",
            "He always eats dinner early","Always he eats dinner early","He eats dinner early always","He always eats dinner early","Test 1.1.2"));
    database.addQuestion(new Questions(
            "Choose the correct sentence:",
            "She never is late","She is never late","never she is late","She is never late","Test 1.1.2"));
    database.addQuestion(new Questions(
            "Complete the sentence: We never ______ fast food.","don't eat ","doesn't eat","eat","eat","Test 1.1.2"));
    database.addQuestion(new Questions(
            "Choose the correct order:","They don't usually travel","They don't travel usually","Usually they don't travel","They don't usually travel","Test 1.1.2"));
    database.addQuestion(new Questions(
            "Complete the sentence: It _______ cold","not is always","always is not","is not always","is not always","Test 1.1.2"));
    database.addQuestion(new Questions(
            "Complete the sentence: We _______ in the garden","sometimes read","some time read","sometimes are reading","sometimes read","Test 1.1.2"));

    //Test 1.1.3
    database.addTests(new Tests(
            "Test 1.1.3",
            "this test will evaluate your level in English Grammar","Present Simple vs Present Continuous" ));
    database.addQuestion(new Questions("Complete the sentence: We are studying ______",
            "at the moment","sometimes","never" ,"at the moment","Test 1.1.3"));
    database.addQuestion(new Questions("Choose the correct sentence:","She is not play",
            "She doesn't play","She doesn't not play","She doesn't play","Test 1.1.3"));
    database.addQuestion(new Questions("Complete the sentence:These books _____ hard",
            "is not","not are","are not","are not","Test 1.1.3"));
    database.addQuestion(new Questions("Choose the correct word:My sister and I _____ going shopping"
            ,"am","are","is","are","Test 1.1.3"));
    database.addQuestion(new Questions("Complete the sentence: She _____ music very much",
            " likes "," like " ," doesn't likes"," likes ","Test 1.1.3"));
    database.addQuestion(new Questions("Choose the correct verb: we sometimes _____ at the library."
            ,"are studying","studies","study","study","Test 1.1.3"));
    //Test 1.1.4
    database.addTests(new Tests(
            "Test 1.1.4",
            "this test will evaluate your level in English Grammar","Questions" ));
    database.addQuestion(new Questions("Choose the correct order for a question:",
            "What you are doing?","What are doing you","What are you doing?","What are you doing?","Test 1.1.4"));
    database.addQuestion(new Questions("Complete the sentence:Does she ______?",
            "study","studies","not studies","study","Test 1.1.4"));
    database.addQuestion(new Questions("Choose the correct question:","Do we has a class?",
            "We do have a class?","Do we have a class?","Do we have a class?","Test 1.1.4"));
    database.addQuestion(new Questions("Complete the sentence:Is he ______?",
            "study","studies","studying","studying","Test 1.1.4"));
    database.addQuestion(new Questions("Choose the correct order for a question:",
            "Did they left?","they did leave? ","Did they leave?","Did they leave?","Test 1.1.4"));
    database.addQuestion(new Questions("Complete the sentence:Has she ______ the book?",
            "written","wrote","write","written","Test 1.1.4"));


    //Test 1.1.5
    database.addTests(new Tests(
            "Test 1.1.5",
            "this test will evaluate your level in English Grammar","Negatives" ));
    database.addQuestion(new Questions("Choose the correct order for the sentence:",
            "You are not leaving.","Not you are leaving.","You are leaving not.","You are not leaving.","Test 1.1.5"));
    database.addQuestion(new Questions("Complete the sentence:She doesn't ______?",
            "study","studies","not studies","study","Test 1.1.5"));
    database.addQuestion(new Questions( "Choose the correct sentence:",
            "We don't had a class.","We don't has a class.","We don't have a class.","We don't have a class.","Test 1.1.5"));
    database.addQuestion(new Questions( "Complete the sentence:He is not ______.",
            "study","studies","studying","studying","Test 1.1.5"));
    database.addQuestion(new Questions( "Choose the correct sentence:",
            "They not eat ","They didn't eat.","Did they didn't ate.","They didn't eat.","Test 1.1.5"));
    database.addQuestion(new Questions("Complete the sentence: Afnan didn't ______.",
            "come over","came over","comes over","come over","Test 1.1.5"));

    // intermediate level tests
    //Test 1.2.1
    database.addTests(new Tests(
            "Test 1.2.1",
            "this test will evaluate your level in English Grammar","English Modal Verbs | Can - Could - May - Might" ));
    database.addQuestion(new Questions("Which sentence is correct: _________ when I was young?","I could swim","I can swim","I may swim","I could swim","Test 1.2.1"));
    database.addQuestion(new Questions("Choose the correct verb: She might _______","comes over","come over","came over","come over","Test 1.2.1"));
    database.addQuestion(new Questions("I _____ English now","can spoke","could speak","can speak","can speak","Test 1.2.1"));
    database.addQuestion(new Questions("I am not sure, I ______ today.","might go out","go out","am going out","might go out","Test 1.2.1"));
    database.addQuestion(new Questions("It's cloudy today, It ______ ."," rains "," may rain ","may rains"," may rain ","Test 1.2.1"));
    database.addQuestion(new Questions("When I was younger I ______ swim, but now I ______.","couldn't - can","cannot - could","can - could","couldn't - can","Test 1.2.1"));

    //Test 1.2.2
    database.addTests(new Tests(
            "Test 1.2.2",
            "this test will evaluate your level in English Grammar","Should - Must - Have to | English Modal Verbs" ));
    database.addQuestion(new Questions("Choose the correct sentence:","you must obey your parents","you must not obey your parents","you mustn't obey your parents","you must obey your parents","Test 1.2.2"));
    database.addQuestion(new Questions( "Complete the sentence:the student_________.","have to study","has to study","has study","has to study","Test 1.2.2"));
    database.addQuestion(new Questions("You look tired, you _______ some rest.","should got","shouldn't get","should get","should get","Test 1.2.2"));
    database.addQuestion(new Questions( "As a muslim, You ________ pray five times a day.","must","should","have to","must","Test 1.2.2"));
    database.addQuestion(new Questions("the house is clean, You _______ clean it today","haven't to","don't have to","have to","don't have to","Test 1.2.2"));
    database.addQuestion(new Questions("She has exams in few days, she _______",
            "have to study","should studies","should study","should study","Test 1.2.2"));

    database.addTests(new Tests(
            "Test 1.2.3",
            "this test will evaluate your level in English Grammar","Present Perfect vs Past Simple" ));
    database.addQuestion(new Questions("Choose the correct verb:I am a teacher and I _______ for 3 years ","have teach","have taught","taught","have taught","Test 1.2.3"));
    database.addQuestion(new Questions("Choose the correct verb:She _______ to her home town 9 days ago.","travelled","have travelled","has travelled","travelled","Test 1.2.3"));
    database.addQuestion(new Questions("Complete: He has studied English _______","last year","since middle school","yesterday","since middle school","Test 1.2.3"));
    database.addQuestion(new Questions("Choose the correct sentence:","I have eat dinner","I have ate dinner","I have eaten dinner","I have eaten dinner","Test 1.2.3"));
    database.addQuestion(new Questions("Complete: They cleaned the house","last night","for 2 hours","since 3 o'clock","last night","Test 1.2.3"));
    database.addQuestion(new Questions("Complete: We've _____ had dinner","now","already","an hour ago","already","Test 1.2.3"));

    database.addTests(new Tests(
            "Test 1.2.4",
            "this test will evaluate your level in English Grammar","Future Continuous vs Future Perfect vs Future Perfect Continuous" ));
    database.addQuestion(new Questions("Complete: We will have been ______.","working","work","worked","working","Test 1.2.4"));
    database.addQuestion(new Questions("Complete: She will _______.","has worked","have worked","have work","have worked","Test 1.2.4"));
    database.addQuestion(new Questions("Complete: I will _______.","have lunch","has lunch","had lunch","have lunch","Test 1.2.4"));
    database.addQuestion(new Questions("Choose the correct verb: I promise, I ______ hard","will have worked","will work","will have been working","will work","Test 1.2.4"));
    database.addQuestion(new Questions("Choose time expression: They will have finished ______.","yesterday","by 7 o'clock","now","by 7 o'clock","Test 1.2.4"));
    database.addQuestion(new Questions("Choose the correct sentence:",
            "Rami won't visit us","Rami won't visits us","Rami won't not visit us","Rami won't visit us","Test 1.2.4"));

    database.addTests(new Tests(
            "Test 1.2.5",
            "this test will evaluate your level in English Grammar","Action Verbs vs State Verbs" ));
    database.addQuestion(new Questions("Choose the correct verb: My major is CS, I ______","like","am liking","likes","like","Test 1.2.5"));
    database.addQuestion(new Questions("Choose the correct verb: ______! there is a butterfly in the room.","looking","looks","look","look","Test 1.2.5"));
    database.addQuestion(new Questions("Complete: the cake _____ like cherry.","is smelling","smell","smells","smells","Test 1.2.5"));
    database.addQuestion(new Questions("Complete: my brother doesn't eat fish, he______ it.","is hating","hates","hate","hates","Test 1.2.5"));
    database.addQuestion(new Questions("Choose the correct sentence:","She loves music","She love music","She is loving music","She loves music","Test 1.2.5"));
    database.addQuestion(new Questions("Choose the correct sentence:",
            "I am understand the lesson","I understand the lesson","I am understanding the lesson","I understand the lesson","Test 1.2.5"));

    //advanced level tests
    database.addTests(new Tests(
            "Test 1.3.1",
            "this test will evaluate your level in English Grammar","All Tenses" ));
    database.addQuestion(new Questions("Complete: We will have been ______.","working","work","worked","working","Test 1.3.1"));
    database.addQuestion(new Questions("Choose time expression: They will have finished ______.","yesterday","by 7 o'clock","now","by 7 o'clock","Test 1.3.1"));
    database.addQuestion(new Questions( "Complete: They cleaned the house","last night","for 2 hours","since 3 o'clock","last night","Test 1.3.1"));
    database.addQuestion(new Questions("Choose the correct verb:I am a teacher and I _______ for 3 years ","have teach","have taught","taught","have taught","Test 1.3.1"));
    database.addQuestion(new Questions("Complete the sentence: We are studying ______",
            "at the moment","sometimes","never" ,"at the moment","Test 1.3.1"));
    database.addQuestion(new Questions("Complete:She _______ the holy mosque _____ week",
            "visit - every","visits - every","visits - last","visits - every","Test 1.3.1"));

    database.addTests(new Tests(
            "Test 1.3.2",
            "this test will evaluate your level in English Grammar","Question Tags" ));
    database.addQuestion(new Questions("Complete: You like your gift,______?","Don't you","Didn't you","Do you","Don't you","Test 1.3.2"));
    database.addQuestion(new Questions("Complete: The cake is tasty,_____ it?","is","isn't","does","isn't","Test 1.3.2"));
    database.addQuestion(new Questions("Complete: We are not leaving,______?","aren't we","we are","are we","are we","Test 1.3.2"));
    database.addQuestion(new Questions( "Choose correct verb: _________ not here,is she?","She is","She isn't","She does","She is","Test 1.3.2"));
    database.addQuestion(new Questions("Choose correct verb: _________ shopping, didn't she?","She didn't go","She went","She goes","She went","Test 1.3.2"));
    database.addQuestion(new Questions("Choose correct verb: You _________ a cat, don't you?","haven't","do","have","have","Test 1.3.2"));

    database.addTests(new Tests(
            "Test 1.3.3",
            "this test will evaluate your level in English Grammar","Conditionals" ));
    database.addQuestion(new Questions("Complete: If you _____ hard, you will succeeded.",
            "will study","studied","study","study","Test 1.3.3"));
    database.addQuestion(new Questions("Complete: If she had come to the party, she ______ enjoyed.",
            "would have","would has","will","would have","Test 1.3.3"));
    database.addQuestion(new Questions("If he ______ to the meeting, he would've learned a lot",
            "come","had come","had came","had come","Test 1.3.3"));
    database.addQuestion(new Questions("Complete: If you leave the ice outside, it ______.",
            "will melts","melts","will melt","melts","Test 1.3.3"));
    database.addQuestion(new Questions("We will travel in the holidays if we _____ enough money",
            "had","have","has","have","Test 1.3.3"));
    database.addQuestion(new Questions("Complete with the correct verb: if she _______,",
            "speaks","will speak","speak","speaks","Test 1.3.3"));

    database.addTests(new Tests(
            "Test 1.3.4",
            "this test will evaluate your level in English Grammar","Passive Voice" ));
    database.addQuestion(new Questions("Change to passive: They built a house",
            "a house is built","a house built","a house was built","a house was built","Test 1.3.4"));
    database.addQuestion(new Questions("Change to passive: We are going to make a cake",
            "a cake is going to be made","a cake was going to be made","a cake is going to me make","a cake is going to be made","Test 1.3.4"));
    database.addQuestion(new Questions("Choose the correct sentence:",
            "a meal will be prepared","a meal will prepared","a meal will be prepared","a meal will be prepared","Test 1.3.4"));
    database.addQuestion(new Questions("Choose the correct sentence:",
            "a book has written","a book has been written","a book has been wrote","a book has been written","Test 1.3.4"));
    database.addQuestion(new Questions("Change to passive: Zain is drawing a painting",
            "a painting is being drawn by Zain","a painting is being drew by Zain","a painting is being drawn with Zain","a painting is being drawn by Zain","Test 1.3.4"));
    database.addQuestion(new Questions("Change to passive: Jhon teaches English at our school",
            "English is taught at our school for Jhon","English is teach at our school by Jhon","English is taught at our school by Jhon","English is taught at our school by Jhon","Test 1.3.4"));

    database.addTests(new Tests(
            "Test 1.3.5",
            "this test will evaluate your level in English Grammar","Indirect Speech" ));
    database.addQuestion(new Questions("Change to indirect speech:Sara said:I always do my homework on time",
            "She always did her homework on time","I always did my homework on time","She always does her homework on time","She always did her homework on time","Test 1.3.5"));
    database.addQuestion(new Questions("Change to indirect speech:Amed said:I watched a movie yesterday",
            "He watched a movie the other day","He had watched a movie the other day","He has watched a movie the other day","He had watched a movie the other day","Test 1.3.5"));
    database.addQuestion(new Questions("Choose the correct sentence:They said:",
            "they would come over tomorrow","they will come over the next day","they would come over the next day","they would come over the next day","Test 1.3.5"));
    database.addQuestion(new Questions("Choose the correct sentence:She said:",
            "She was coming next month","She was coming the following month","She is coming next month","She was coming the following month","Test 1.3.5"));
    database.addQuestion(new Questions("Change to indirect speech:He said: I didn't go to school.",
            "I hadn't gone to school","He hadn't gone to school","He hasn't gone to school","He hadn't gone to school","Test 1.3.5"));
    database.addQuestion(new Questions("Change to indirect speech:Maha said: My sister doesn't work",
            "her sister doesn't work","her sister didn't work","my sister didn't work","her sister didn't work","Test 1.3.5"));
}

}
