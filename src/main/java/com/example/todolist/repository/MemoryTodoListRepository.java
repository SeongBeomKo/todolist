package com.example.todolist.repository;

import com.example.todolist.model.TodoList;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryTodoListRepository implements TodoListRepository {

    ConcurrentHashMap<LocalDate, Map<Long, TodoList>> allList = new ConcurrentHashMap<>();
    private static long sequence = 0L;


    /* 데이터 저장 */
    @Override
    public TodoList save(TodoList todoList) {
        todoList.setId(++sequence);
        Map<Long, TodoList> listForTheDate = allList.containsKey(todoList.getDueDate()) ?
                allList.get(todoList.getDueDate()) : new HashMap<>();
        listForTheDate.put(todoList.getId(), todoList);
        allList.put(todoList.getDueDate(), listForTheDate);
        return todoList;
    }

    /* 데이터 삭제 */
    @Override
    public void deleteById(Long id, LocalDate date) {
        allList.get(date).remove(id);
    }

    /* 날짜별 데이터 찾기 */
    @Override
    public List<TodoList> findByDueDate(LocalDate date) {
        List<TodoList> listForTheDate = new ArrayList<>();
        Map<Long, TodoList> mapForTheDate = allList.get(date);
        for (Long id : mapForTheDate.keySet()) {
            listForTheDate.add(mapForTheDate.get(id));
        }
        return listForTheDate;
    }

    /* 날짜와 아이디 값으로 distinct 데이터 찾기 */
    @Override
    public TodoList findByDueDateAndId(LocalDate localDate, Long id) {
        return allList.get(localDate).get(id);
    }

    /* 중요도와 순서순으로 정렬된 해당 날짜의 데이터 찾기 */
    @Override
    public List<TodoList> getAllTodosByDueDateOrderByPriorityAndRank(LocalDate date) {
        List<TodoList> todoLists = new ArrayList<>();
        for (LocalDate localDate : allList.keySet()) {
            if (localDate.equals(date)) {
                Map<Long, TodoList> todoListMap = allList.get(localDate);
                for (Long id : todoListMap.keySet()) {
                    todoLists.add(todoListMap.get(id));
                }
            }
        }
        Collections.sort(todoLists, new Comparator<TodoList>() {
            @Override
            public int compare(TodoList o1, TodoList o2) {
                if (o1.getPriority().equals(o2.getPriority())) {
                    return o1.getRank() - o2.getRank();
                } else {
                    return o1.getPriority().compareTo(o2.getPriority());
                }
            }
        });
        return todoLists;
    }

    /* 데이터 값 수정 하기 */
    @Override
    public TodoList update(TodoList todoList) {
        Map<Long, TodoList> listForTheDate = allList.get(todoList.getDueDate());
        listForTheDate.put(todoList.getId(), todoList);
        allList.put(todoList.getDueDate(), listForTheDate);
        return todoList;
    }
}
