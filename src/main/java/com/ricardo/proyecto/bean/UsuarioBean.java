/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ricardo.proyecto.bean;

import com.ricardo.proyecto.model.Usuario;
import com.ricardo.proyecto.service.UsuarioServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    private UploadedFile uploadedFile;
    @Inject
    private UsuarioServiceImpl uService;
   
    private Usuario usuario;

    public UsuarioBean() {
        
    }

    @PostConstruct
    public void init() {
        usuario=new Usuario();
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void upload() {
        System.out.println("File type: " + uploadedFile.getContentType());
        System.out.println("File name: " + uploadedFile.getName());
        System.out.println("File size: " + uploadedFile.getSize() + " bytes");
        try {
            byte[] contents = IOUtils.toByteArray(uploadedFile.getInputStream());
            usuario.setFoto(contents);
            uService.save(usuario);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
