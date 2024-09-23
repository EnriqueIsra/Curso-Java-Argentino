package org.aguzman.apiservlet.webapp.bbdd.tarea9.controllers;

import org.aguzman.apiservlet.webapp.bbdd.tarea9.services.CursoService;
import org.aguzman.apiservlet.webapp.bbdd.tarea9.services.CursoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import org.aguzman.apiservlet.webapp.bbdd.tarea9.models.Curso;

@WebServlet({"/index.html", "/cursos"})
public class CursoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CursoService service = new CursoServiceImpl(conn);
        List<Curso> cursos = service.listar();

        req.setAttribute("titulo", "Tarea 9: Listado de cursos");
        req.setAttribute("cursos", cursos);
        getServletContext().getRequestDispatcher("/listar.jsp").forward(req, resp);
    }
}
