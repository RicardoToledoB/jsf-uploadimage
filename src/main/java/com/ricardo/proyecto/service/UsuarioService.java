/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ricardo.proyecto.service;

import com.ricardo.proyecto.model.Usuario;
import java.util.List;

/**
 *
 * @author ricardotoledo
 */
public interface UsuarioService {
    public void save(Usuario u);
    public void edit(Usuario u);
    public void delete(Usuario u);
    public List<Usuario> list();
    public Usuario find(Usuario u);
}
