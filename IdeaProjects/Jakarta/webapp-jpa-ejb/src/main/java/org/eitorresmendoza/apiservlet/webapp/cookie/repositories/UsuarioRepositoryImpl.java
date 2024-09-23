package org.eitorresmendoza.apiservlet.webapp.cookie.repositories;

import jakarta.inject.Inject;
import org.eitorresmendoza.apiservlet.webapp.cookie.configs.MysqlConn;
import org.eitorresmendoza.apiservlet.webapp.cookie.configs.Repository;
import org.eitorresmendoza.apiservlet.webapp.cookie.models.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RepositoryJdbc
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Inject
    @MysqlConn
//    @Named("conn")
    private Connection conn;

//    public UsuarioRepositoryImpl(Connection conn) {
//        this.conn = conn;
//    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from usuarios")) {
            while (rs.next()) {
                Usuario u = getUsuario(rs);
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("select * from usuarios where id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0){
            sql = "update usuarios set username = ?, password = ?, email = ? where id = ?";
        } else {
            sql = "insert into usuarios (username, password, email) values(?, ?, ?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            if (usuario.getId() != null && usuario.getId() > 0){
                stmt.setLong(4, usuario.getId());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from usuarios where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("select * from usuarios where username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    private static Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }
}
