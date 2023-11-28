package br.ce.wcaquina.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo taskRepo; 
	
	@InjectMocks
	private TaskController controller = new TaskController();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void dontSaveNullDescTask() {
		Task task = new Task(); 
		task.setDueDate(LocalDate.now());

		try {
			controller.save(task);	
		} catch (ValidationException exception) {
			Assert.assertEquals("Fill the task description", exception.getMessage());
		}
	}
	
	@Test
	public void dontSaveNoDateTask() {
		Task task = new Task(); 
		task.setTask("Descrição");
		try {
			controller.save(task);	
		} catch (ValidationException exception) {
			Assert.assertEquals("Fill the due date", exception.getMessage());
		}
	}
	
	@Test
	public void dontSavePastDateTask() {
		Task task = new Task(); 
		task.setTask("Descrição");
		task.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			controller.save(task);	
		} catch (ValidationException exception) {
			Assert.assertEquals("Due date must not be in past", exception.getMessage());
		}
	}
	
	@Test
	public void saveTask() throws Exception {
		Task task = new Task(); 
		task.setTask("Descrição");
		task.setDueDate(LocalDate.of(2034, 01, 01));
		controller.save(task);	
	}
}
