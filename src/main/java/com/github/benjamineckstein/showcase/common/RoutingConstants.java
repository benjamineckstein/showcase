package com.github.benjamineckstein.showcase.common;

public class RoutingConstants {

    /**
     * GET, PUT, DELETE
     */
    public static final String URL_EXPERTISE_ID = "/api/expertise/{Uuid}";

    /**
     * GET, POST
     */
    public static final String URL_EXPERTISE = "/api/expertise/";

    /**
     * GET, POST
     */
    public static final String URL_EMPLOYEES = "/api/employees/";

    /**
     * GET, PUT, DELETE
     */
    public static final String URL_EMPLOYEES_ID = "/api/employees/{Uuid}";

    /**
     * GET, POST, PUT
     */
    public static final String URL_SKILLS = "/api/skills/";

    /**
     * GET, DELETE
     */
    public static final String URL_SKILLS_ID = "/api/skills/{Uuid}";

    /**
     * GET
     */
    public static final String URL_EMPLOYEES_FIND = "/api/employees/find/{name}";

    /**
     * GET
     */
    public static final String URL_EXPERTISE_SEARCH = "/api/expertise/find/{keyword}";

    /**
     * GET
     */
    public static final String URL_SKILLS_FIND = "/api/skills/find/{name}";

}
