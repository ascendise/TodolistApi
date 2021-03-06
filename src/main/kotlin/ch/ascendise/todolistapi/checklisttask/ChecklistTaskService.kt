package ch.ascendise.todolistapi.checklisttask

import ch.ascendise.todolistapi.checklist.Checklist
import ch.ascendise.todolistapi.checklist.ChecklistService
import ch.ascendise.todolistapi.task.TaskService
import org.springframework.stereotype.Service

@Service
class ChecklistTaskService(
    val taskService: TaskService,
    val checklistService: ChecklistService
){

    fun addTask(checklistTask: ChecklistTask): Checklist{
        val task = taskService.getById(checklistTask.userId, checklistTask.taskId)
        val checklist = checklistService.getChecklist(checklistTask.checklistId, checklistTask.userId)
        if(checklist.tasks.contains(task))
        {
            return checklist
        }
        checklist.tasks.add(task)
        return checklistService.update(checklist)
    }

    fun removeTask(checklistTask: ChecklistTask): Checklist {
        val checklist = checklistService.getChecklist(checklistTask.checklistId, checklistTask.userId)
        checklist.tasks.removeIf { it.id == checklistTask.taskId }
        return checklistService.update(checklist)
    }

    fun getRelations(userId: Long): List<ChecklistTask> {
        val checklists = checklistService.getChecklists(userId)
        val relations = mutableListOf<ChecklistTask>()
        for(checklist in checklists) {
            for(task in checklist.tasks) {
                relations.add(ChecklistTask(checklist.id, task.id, userId))
            }
        }
        return relations
    }
}