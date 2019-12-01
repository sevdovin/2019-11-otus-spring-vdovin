package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;

import java.util.Scanner;

public interface ExamService {

    ExamResult examine(Scanner scanner);
}
