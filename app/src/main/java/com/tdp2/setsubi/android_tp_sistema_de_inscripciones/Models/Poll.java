package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Poll
{
    public enum PollType
    {
        CALIFICATION,
        INTERESTING,
        DIFICULTY,
        YES_NO,
        FREE_TEXT
    }

    private PollType type;
    private String question;

    public Poll(String question, PollType type)
    {
        this.question = question;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public PollType getType() {
        return type;
    }

    public static class DificultyPoll extends Poll
    {
        private int dificulty = 5;

        public DificultyPoll(String question)
        {
            super(question, PollType.DIFICULTY);
        }

        public void setDificulty(int dificulty)
        {
            this.dificulty = dificulty;
        }

        public int getDificulty() {
            return dificulty;
        }
    }
    public static class CalificationPoll extends Poll
    {
        public CalificationPoll(String question)
        {
            super(question, PollType.CALIFICATION);
        }

        private int calification = 5;

        public int getCalification()
        {
            return calification;
        }

        public void setCalification(int calification)
        {
            this.calification = calification;
        }
    }

    public static class InterstingPoll extends Poll
    {
        private int interesting = 5;
        public InterstingPoll(String question)
        {
            super(question, PollType.INTERESTING);
        }

        public void setInteresting(int interesting)
        {
            this.interesting = interesting;
        }

        public int getInteresting() {
            return interesting;
        }
    }

    public static class YesNoPoll extends Poll
    {
        private boolean yes = true;


        public YesNoPoll(String question)
        {
            super(question, PollType.YES_NO);
        }

        public void setYes(boolean yes)
        {
            this.yes = yes;
        }

        public boolean isYes() {
            return yes;
        }
    }

    public static class FreeTextPoll extends Poll
    {
        private String text = "";

        public FreeTextPoll(String question)
        {
            super(question, PollType.FREE_TEXT);
        }

        public void setText(String string)
        {
            this.text = string;
        }

        public String getText() {
            return text;
        }
    }
}
