package com.platzi.profesoresplatzi.dao;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.platzi.profesoresplatzi.model.Teacher;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Repository
@Transactional
public class TeacherDaoImpl extends AbstractSession implements TeacherDao{



	public void saveTeacher(Teacher teacher) {
		
		getSession().persist(teacher);
	}

	public void deleteTeacherById(Long idTeacher) {
		Teacher teacher = findById(idTeacher);
		if (teacher != null) {
			
			Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedias().iterator();
			while (i.hasNext()) {
				TeacherSocialMedia teacherSocialMedia = i.next();
				i.remove();
				getSession().delete(teacherSocialMedia);
				
			}
			teacher.getTeacherSocialMedias().clear();			
			getSession().delete(teacher);
		}
	}

	public void updateTeacher(Teacher teacher) {
		getSession().update(teacher);
		
	}

	public List<Teacher> findAllTeachers() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Teacher").list();
	}

	public Teacher findById(Long idTeacher) {
		return (Teacher) getSession().get(Teacher.class, idTeacher);
	}

	public Teacher findByName(String name) {
		return (Teacher) getSession().createQuery(
				"from Teacher where name = :name").setParameter("name", name).uniqueResult();
	}

}
