package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.Arrays;
import java.util.List;

public class PollCreator
{
    public static List<Poll> createNewPoll()
    {
        return Arrays.asList(
                new Poll.CalificationPoll("Opinión general del curso"),
                new Poll.InterstingPoll("¿Cómo te resultaron los temas de la materia?"),
                new Poll.YesNoPoll("¿Los temas de la materia están actualizados?"),
                new Poll.CalificationPoll("Nivel de las Clases Teórcias del curso"),
                new Poll.CalificationPoll("Nivel de las Clases Prácticas del curso"),
                new Poll.DificultyPoll("Dificultad del curso"),
                new Poll.DificultyPoll("Dificultad del TP"),
                new Poll.FreeTextPoll("Comentarios sobre el curso")
        );
    }
}
