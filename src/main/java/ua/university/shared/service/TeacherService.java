package ua.university.shared.service;

import ua.university.client.entity.TeacherDTO;
import ua.university.shared.Teacher;

public interface TeacherService {
    Teacher save(TeacherDTO teacherDTO);
    void deleteById(int id);
    void update(TeacherDTO teacherFrom, int teacherToId);
}
