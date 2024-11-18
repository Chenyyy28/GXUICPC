package manage.gxuicpc.entity;

import lombok.Data;

@Data
public class Route {
    private Long id;
    private String link;
    private String icon;
    private String parent;
    private String pageName;
    private String role;
    private String name;
}