package database.project.library.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String name;
    
    @Override
    public String toString() {
        return String.valueOf(id) + " " + this.name;
    }

}
