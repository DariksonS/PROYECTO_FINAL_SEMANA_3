package util;

import java.util.ArrayList;
import java.util.List;

//Almacén genérico reutilizable para cualquier entidad con ID
public class Repositorio<T extends Identificable>
{
    private List<T> elementos;

    public Repositorio()
    {
        elementos = new ArrayList<>();
    }

    public void agregar(T elemento)
    {
        elementos.add(elemento);
    }

    //Retorna null si no lo encuentra
    public T buscarPorId(int id)
    {
        for (T elemento : elementos)
        {
            if (elemento.getId() == id)
            {
                return elemento;
            }
        }
        return null;
    }

    //Retorna true si lo eliminó y false si no existía
    public boolean eliminar(int id)
    {
        for (int i = 0; i < elementos.size(); i++)
        {
            if (elementos.get(i).getId() == id)
            {
                elementos.remove(i);
                return true;
            }
        }
        return false;
    }

    //Devuelve una copia para no exponer la lista original
    public List<T> listar()
    {
        return new ArrayList<>(elementos);
    }

    public int contar()
    {
        return elementos.size();
    }
}
