package com.ricardo.proyecto.bean;

import com.ricardo.proyecto.model.Usuario;
import static com.ricardo.proyecto.model.Usuario_.foto;
import com.ricardo.proyecto.service.UsuarioServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    private UploadedFile uploadedFile;

    @Inject
    private UsuarioServiceImpl uService;
    private Usuario usuarioView;
    private Usuario usuario;
    private List<Usuario> lista;

    public UsuarioBean() {

    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        //lista=new ArrayList<Usuario>();
        usuarioView = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLista() {

        return uService.list();
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Usuario getUsuarioView() {
        return usuarioView;
    }

    public String setUsuarioView(Usuario usuarioView) {
        this.usuarioView = uService.find(usuarioView);
        return "view?faces-redirect=true";
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

    public String getImage(Usuario u) {
        Usuario user = uService.find(u);
        System.out.println("NOMBRE" + user.getNombre());
        byte[] image = user.getFoto();
        String imgDataBase64=encode(image);
        return imgDataBase64;
        /*FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            Usuario user = uService.find(u);
            System.out.println("NOMBRE" + user.getNombre());
            byte[] imageInByteArray = user.getFoto();
            return new DefaultStreamedContent(new ByteArrayInputStream(imageInByteArray), "image/png");

        }*/
    }
    public static String encode(byte[] data)
    {
        char[] tbl = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
            'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
            'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
            'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/' };

        StringBuilder buffer = new StringBuilder();
        int pad = 0;
        for (int i = 0; i < data.length; i += 3) {

            int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
            if (i + 1 < data.length) {
                b |= (data[i+1] & 0xFF) << 8;
            } else {
                pad++;
            }
            if (i + 2 < data.length) {
                b |= (data[i+2] & 0xFF);
            } else {
                pad++;
            }

            for (int j = 0; j < 4 - pad; j++) {
                int c = (b & 0xFC0000) >> 18;
                buffer.append(tbl[c]);
                b <<= 6;
            }
        }
        for (int j = 0; j < pad; j++) {
            buffer.append("=");
        }

        return buffer.toString();
    }
}
