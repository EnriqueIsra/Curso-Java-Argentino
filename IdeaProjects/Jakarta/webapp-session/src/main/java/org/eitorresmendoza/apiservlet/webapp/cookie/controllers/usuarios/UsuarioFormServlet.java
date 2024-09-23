package org.eitorresmendoza.apiservlet.webapp.cookie.controllers.usuarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eitorresmendoza.apiservlet.webapp.cookie.models.Usuario;
import org.eitorresmendoza.apiservlet.webapp.cookie.services.UsuarioService;
import org.eitorresmendoza.apiservlet.webapp.cookie.services.UsuarioServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/usuarios/form")
public class UsuarioFormServlet extends HttpServlet {

    @Inject
    private UsuarioService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Connection conn = (Connection) req.getAttribute("conn");
//        UsuarioService service = new UsuarioServiceImpl(conn);
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }
        Usuario usuario = new Usuario();
        if (id > 0){
            Optional<Usuario> o = service.porId(id);
            if (o.isPresent()) {
                usuario = o.get();
            }
        }
        req.setAttribute("usuario", usuario);
        req.setAttribute("title", req.getAttribute("title") + ": Registro de usuario");
        getServletContext().getRequestDispatcher("/usuarios/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Connection conn = (Connection) req.getAttribute("conn");
//        UsuarioService service = new UsuarioServiceImpl(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        Map<String, String> errores = new HashMap<>();

        if (username == null || username.isBlank()){
            errores.put("username", "El username no puede ser vacío");
        } else if (username.length() > 12){
            errores.put("username", "El username no debe tener más de 12 caracteres");
        }
        if (password == null || password.isBlank()){
            errores.put("password", "El password no puede ser vacío");
        }
        if (email == null || email.isBlank()){
            errores.put("email", "El email no puede ser vacío");
        }

        Usuario usuario = new Usuario();

        if (id > 0){
            Optional<Usuario> o = service.porId(id);
            if (o.isPresent()){
                usuario = o.get();
            }
        }
        usuario.setUsername(username);
        usuario.setEmail(email);

        if (password != null && !password.isBlank()){
            usuario.setPassword(password);
        }
        if (errores.isEmpty()){
            service.guardar(usuario);
            resp.sendRedirect(req.getContextPath() + "/usuarios");
        } else {
            req.setAttribute("errores", errores);
            req.setAttribute("usuario", usuario);
            req.setAttribute("title", req.getAttribute("title") + ": Editar el usuario");
            getServletContext().getRequestDispatcher("/usuarios/form.jsp").forward(req, resp);
        }
    }
}
