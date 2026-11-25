package dao;

import java.util.List;

import modelo.Noticia;

//Lo que debe saber hacer cualquier lugar donde se guarden noticias
public interface AlmacenNoticias
{
    Noticia guardar(Noticia noticia);

    Noticia buscarPorId(int id);

    boolean actualizar(Noticia noticia);

    boolean eliminar(int id);

    List<Noticia> listar();

    int contar();
}
