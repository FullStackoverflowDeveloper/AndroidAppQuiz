package com.example.justquiz;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsBank {

    private static List<QuestionsList> mysqlQuestionsLists = new ArrayList<>();
    private static List<QuestionsList> oracleQuestionsLists = new ArrayList<>();

    public static void setMysqlQuestionsLists(List<QuestionsList> mysqlQuestionsLists) {
        QuestionsBank.mysqlQuestionsLists = mysqlQuestionsLists;
    }

    public static void setOracleQuestionsLists(List<QuestionsList> oracleQuestionsLists) {
        QuestionsBank.oracleQuestionsLists = oracleQuestionsLists;
    }

    public static void setMariaQuestionsLists(List<QuestionsList> mariaQuestionsLists) {
        QuestionsBank.mariaQuestionsLists = mariaQuestionsLists;
    }

    public static void setAuroraQuestionsLists(List<QuestionsList> auroraQuestionsLists) {
        QuestionsBank.auroraQuestionsLists = auroraQuestionsLists;
    }

    public static List<QuestionsList> getMysqlQuestionsLists() {
        return mysqlQuestionsLists;
    }

    public static List<QuestionsList> getOracleQuestionsLists() {
        return oracleQuestionsLists;
    }

    public static List<QuestionsList> getMariaQuestionsLists() {
        return mariaQuestionsLists;
    }

    public static List<QuestionsList> getAuroraQuestionsLists() {
        return auroraQuestionsLists;
    }

    private static List<QuestionsList> mariaQuestionsLists = new ArrayList<>();
    private static List<QuestionsList> auroraQuestionsLists = new ArrayList<>();

    public static List<QuestionsList> getQuestions(String selectedTopicName){
        switch (selectedTopicName){
            case "oracle":
                return oracleQuestionsLists;
            case "maria":
                return mariaQuestionsLists;
            case "mysql":
                return mysqlQuestionsLists;
            default:
                return auroraQuestionsLists;
        }
    }

    public static List<QuestionsList> getRealtimeDatabaseData(String getRef, String getChild){
        ArrayList questionsLists = new ArrayList();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(getRef).child(getChild);
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsLists.clear();
                ArrayList questions = (ArrayList) snapshot.getValue();

                for (Integer i = 0; i < questions.size(); i++) {
                    HashMap question = (HashMap) questions.get(i);


                    final QuestionsList q = new QuestionsList((String)question.get("question"),
                            (String)question.get("option1"),(String)question.get("option2"),
                            (String)question.get("option3"), (String)question.get("option4"),
                            (String)question.get("answer"),(String)question.get("userSelectedAnswer"));
                    questionsLists.add(q);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return questionsLists;
    }
}
