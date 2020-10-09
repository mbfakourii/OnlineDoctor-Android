package chamran.ir.onlinedoctor.Question.struct_recyclerview;


import chamran.ir.onlinedoctor.Question.adapter.Question_RecyclerAdapter;

/*
 *   struct question
 */
public class Question_Info extends SeparatorQuestion {
    public String answer1;
    public String tag_answer;

    //constructor item question
    public Question_Info(String answer1, String tag_answer) {
        this.answer1 = answer1;
        this.tag_answer = tag_answer;
        this.type = Question_RecyclerAdapter.QUESTION;
    }
}
