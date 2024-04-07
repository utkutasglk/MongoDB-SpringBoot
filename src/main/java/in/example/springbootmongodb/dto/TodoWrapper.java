package in.example.springbootmongodb.dto;

import in.example.springbootmongodb.model.TodoDTO;

import java.util.List;

public class TodoWrapper {

    private List<TodoDTO> todoDTOS;

    public List<TodoDTO> getTodoDTOS(){
        return todoDTOS;
    }

    public void setTodoDTOS(List<TodoDTO> todoDTOS){
        this.todoDTOS = todoDTOS;
    }
}
