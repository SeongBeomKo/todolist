package com.example.todolist;

import com.example.todolist.dto.request.DataRequestDto;
import com.example.todolist.dto.request.PostRequestDto;
import com.example.todolist.dto.request.UpdateRequestDto;
import com.example.todolist.dto.response.DataResponseDto;
import com.example.todolist.model.TodoList;
import com.example.todolist.repository.MemoryTodoListRepository;
import com.example.todolist.service.MainService;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainServiceTest {


    static MemoryTodoListRepository memoryTodoListRepository;
    static MainService mainService;

    private DataRequestDto dataRequestDto;
    private PostRequestDto postRequestDto1;
    private PostRequestDto postRequestDto2;
    private PostRequestDto postRequestDto3;
    private UpdateRequestDto updateRequestDto1;
    private UpdateRequestDto updateRequestDto2;
    private UpdateRequestDto updateRequestDto3;

    private Long postId1;
    private Long postId2;
    private Long postId3;

    @BeforeAll
    static void setupbefore() {
        memoryTodoListRepository = new MemoryTodoListRepository();
        mainService = new MainService(memoryTodoListRepository);
    }


    @BeforeEach
    void setup() {

        dataRequestDto = DataRequestDto.builder()
                .duedate(LocalDate.parse("2022-05-03"))
                        .build();

        postRequestDto1 = PostRequestDto.builder()
                .title("밥먹기")
                .description("냠냠쩝쩝")
                .duedate(LocalDate.parse("2022-05-03"))
                .build();

        postRequestDto2 = PostRequestDto.builder()
                .title("떡먹기")
                .description("쫄깃쫄깃")
                .duedate(LocalDate.parse("2022-05-03"))
                .build();

        postRequestDto3 = PostRequestDto.builder()
                .title("국먹기")
                .description("후르륵짭짭")
                .duedate(LocalDate.parse("2022-05-03"))
                .build();

        updateRequestDto1 = UpdateRequestDto.builder()
                .title("빵먹기")
                .description("우걱우걱")
                .duedate(LocalDate.parse("2022-05-03"))
                .status("완료")
                .rank(0)
                .priority("B")
                .build();

        updateRequestDto2 = UpdateRequestDto.builder()
                .title("떡먹기")
                .description("쫄깃쫄깃")
                .duedate(LocalDate.parse("2022-05-03"))
                .status("취소")
                .rank(4)
                .priority("S")
                .build();

        updateRequestDto3 = UpdateRequestDto.builder()
                .title("국먹기")
                .description("후르륵짭짭")
                .duedate(LocalDate.parse("2022-05-03"))
                .status("취소")
                .rank(0)
                .priority("B")
                .build();
    }


    @Order(1)
    @Test
    @DisplayName("할일 등록 1")
    void test1() {
        postId1 = mainService.addTodo(postRequestDto1);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(postRequestDto1.getDuedate(), postId1);
        assertEquals(todoList.getTitle(), postRequestDto1.getTitle());
        assertEquals(todoList.getDescription(), postRequestDto1.getDescription());
        assertEquals("B", todoList.getPriority().getPriority());
        assertEquals(0, todoList.getRank());
    }

    @Order(2)
    @Test
    @DisplayName("할일 등록 2")
    void test2() {
        postId2 = mainService.addTodo(postRequestDto2);
        System.out.println(postId2);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(postRequestDto2.getDuedate(), postId2);
        assertEquals(2, mainService.showTodos(dataRequestDto).size());
        assertEquals(todoList.getTitle(), postRequestDto2.getTitle());
        assertEquals(todoList.getDescription(), postRequestDto2.getDescription());
        assertEquals("B", todoList.getPriority().getPriority());
        assertEquals(1, todoList.getRank());
    }

    @Order(3)
    @Test
    @DisplayName("할일 등록 3")
    void test3() {
        postId3 = mainService.addTodo(postRequestDto3);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(postRequestDto3.getDuedate(), postId3);
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        assertEquals(todoList.getTitle(), postRequestDto3.getTitle());
        assertEquals(todoList.getDescription(), postRequestDto3.getDescription());
        assertEquals("B", todoList.getPriority().getPriority());
        assertEquals(2, todoList.getRank());
    }


    @Order(4)
    @Test
    @DisplayName("할일 수정 1")
    void test4() {
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        mainService.updateTodo(1L, updateRequestDto1);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(updateRequestDto1.getDuedate(), 1L);
        assertEquals(todoList.getTitle(), updateRequestDto1.getTitle());
        assertEquals(todoList.getDescription(), updateRequestDto1.getDescription());
        assertEquals("완료", todoList.getStatus().getStatus());
    }

    @Test
    @DisplayName("할일 수정 2")
    void test5() {
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        mainService.updateTodo(2L, updateRequestDto2);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(updateRequestDto2.getDuedate(), 2L);
        assertEquals(todoList.getPriority().getPriority(), updateRequestDto2.getPriority());
        assertEquals(0, todoList.getRank());
        assertEquals("취소", todoList.getStatus().getStatus());
    }

    @Test
    @DisplayName("할일 수정 3")
    void test6() {
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        mainService.updateTodo(3L, updateRequestDto3);

        TodoList todoList = memoryTodoListRepository.findByDueDateAndId(updateRequestDto3.getDuedate(), 3L);
        assertEquals(todoList.getPriority().getPriority(), updateRequestDto3.getPriority());
        assertEquals(0, todoList.getRank());
        assertEquals("취소", todoList.getStatus().getStatus());
    }

    @Test
    @DisplayName("할일 조회 1")
    void test7() {
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        List<DataResponseDto> todoLists = mainService.showTodos(dataRequestDto);

        assertEquals("S", todoLists.get(0).getPriority());
        assertEquals("B", todoLists.get(1).getPriority());
        assertEquals("B", todoLists.get(2).getPriority());
        assertEquals(0, todoLists.get(0).getRank());
        assertEquals(0, todoLists.get(1).getRank());
    }

    @Test
    @DisplayName("할일 삭제 1")
    void test8() {
        assertEquals(3, mainService.showTodos(dataRequestDto).size());
        mainService.removeTodo(3L, dataRequestDto.getDuedate());
        assertEquals(2, mainService.showTodos(dataRequestDto).size());
    }

    @Test
    @DisplayName("할일 조회/삭제 2")
    void test9() {
        List<DataResponseDto> todoLists = mainService.showTodos(dataRequestDto);

        assertEquals("S", todoLists.get(0).getPriority());
        assertEquals("B", todoLists.get(1).getPriority());
        assertEquals(0, todoLists.get(0).getRank());
        assertEquals(0, todoLists.get(1).getRank());

        assertEquals(2, mainService.showTodos(dataRequestDto).size());
        mainService.removeTodo(1L, dataRequestDto.getDuedate());
        assertEquals(1, mainService.showTodos(dataRequestDto).size());

        todoLists = mainService.showTodos(dataRequestDto);

        assertEquals("S", todoLists.get(0).getPriority());
        assertEquals(0, todoLists.get(0).getRank());
    }

}
