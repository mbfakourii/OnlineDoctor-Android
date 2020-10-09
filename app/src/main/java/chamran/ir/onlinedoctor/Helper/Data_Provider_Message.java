package chamran.ir.onlinedoctor.Helper;

public class Data_Provider_Message {
    private String str_question;
    private String str_answer;

    public Data_Provider_Message(String str_question, String str_answer) {
        this.str_question = str_question;
        this.str_answer = str_answer;
    }

    public Data_Provider_Message() {

    }

    public void setQuestion(String str_question) {
        this.str_question = str_question;
    }

    public String getQuestion() {
        return str_question;
    }

    public void setAnswer(String str_answer) {
        this.str_answer = str_answer;
    }


    public String getAnswer() {
        return str_answer;
    }
}
