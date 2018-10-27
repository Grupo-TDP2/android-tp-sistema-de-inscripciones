package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public interface NotificationsService
{
    ServiceResponse<Boolean> sendToken(Student student, String token);
}
