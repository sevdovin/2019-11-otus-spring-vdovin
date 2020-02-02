package ru.otus.svdovin.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework02.domain.ExamResult;
import ru.otus.svdovin.homework02.exception.QuestionsLoadingFailedException;

@Service
public class StartServiceImpl implements StartService {

    private static final String MSG_KEY_ENTER_LASTNAME = "enter.lastname";
    private static final String MSG_KEY_ENTER_FIRSTNAME = "enter.firstname";
    private static final String MSG_KEY_RESULT_SUCCESS = "result.success";
    private static final String MSG_KEY_RESULT_FAIL = "result.fail";
    private static final String RESULT_MESSAGE = "%s, %s %d.";

    String lastName = null;
    String firstName = null;

    private final ExamService examService;
    private  final MessageService messageService;

    public StartServiceImpl(ExamService examService, MessageService messageService) {
        this.examService = examService;
        this.messageService = messageService;
    }

    @Override
    public void startExam() throws QuestionsLoadingFailedException {
        getFIO();
        ExamResult examResult = examService.examine();
        String answer = messageService.getMessageByKey(examResult.isPassed() ? MSG_KEY_RESULT_SUCCESS : MSG_KEY_RESULT_FAIL);
        messageService.printMessage(String.format(RESULT_MESSAGE, lastName + " " + firstName, answer, examResult.getCorrectAnswerCount()));
    }

    private void getFIO() {
        lastName = messageService.readLn(MSG_KEY_ENTER_LASTNAME);
        firstName = messageService.readLn(MSG_KEY_ENTER_FIRSTNAME);
    }
}
