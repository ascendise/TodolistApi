package ch.ascendise.todolistapi.task

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskService(
    val taskRepository: TaskRepository
) {

    fun put(task: Task) {
        if (task.endDate?.isBefore(task.startDate) == true ||
            task.startDate.isBefore(LocalDate.now())
        ) {
            throw InvalidTaskException()
        }
        taskRepository.save(task)
    }

    fun getAll(userId: Long): Set<Task> = taskRepository.findAllByUserId(userId).toSet()
    fun delete(taskId: Long) = taskRepository.deleteById(taskId)


}