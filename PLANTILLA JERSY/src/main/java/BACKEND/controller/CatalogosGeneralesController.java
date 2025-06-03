package BACKEND.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import BACKEND.manager.CatalogosGeneralesManager;
import BACKEND.utils.jsonResult;
import BACKEND.controller.Categoria;

// POJO simple para recibir el JSON en POST/PUT


@Path("Catalogos")
public class CatalogosGeneralesController {

    CatalogosGeneralesManager manager = new CatalogosGeneralesManager();

    @GET
    @Path("/GetCategoriasProductos")
    @Produces("application/json")
    public Response GetCategoriasProductos() {
        try {
            return Response.ok(manager.GetCategoriasProductos()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/Categoria/{id_categoria}/Productos")
    @Produces("application/json")
    public Response GetProductosCategoria(@PathParam("id_categoria") String id_categoria) {
        try {
            return Response.ok(manager.GetProductosCategoria(id_categoria)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new jsonResult(-1, "Error", e.getMessage())).build();
        }
    }

    // Ruta para agregar categoría (POST)
    @POST
    @Path("/Categoria")
    @Consumes("application/json")
    @Produces("application/json")
    public Response AgregarCategoria(Categoria categoria) {
        try {
            boolean ok = manager.AgregarCategoria(categoria.getNombre());
            if (ok) {
                return Response.ok(new jsonResult(1, "Categoría agregada correctamente", null)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity(new jsonResult(-1, "Error al agregar categoría", null)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new jsonResult(-1, "Error", e.getMessage())).build();
        }
    }

    // Ruta para editar categoría (PUT)
    @PUT
    @Path("/Categoria/{id_categoria}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response EditarCategoria(@PathParam("id_categoria") int id_categoria, Categoria categoria) {
        try {
            boolean ok = manager.EditarCategoria(id_categoria, categoria.getNombre());
            if (ok) {
                return Response.ok(new jsonResult(1, "Categoría actualizada correctamente", null)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(new jsonResult(-1, "Categoría no encontrada", null)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new jsonResult(-1, "Error", e.getMessage())).build();
        }
    }

    // Ruta para eliminar categoría (DELETE)
    @DELETE
    @Path("/Categoria/{id_categoria}")
    @Produces("application/json")
    public Response EliminarCategoria(@PathParam("id_categoria") int id_categoria) {
        try {
            boolean ok = manager.EliminarCategoria(id_categoria);
            if (ok) {
                return Response.ok(new jsonResult(1, "Categoría eliminada correctamente", null)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(new jsonResult(-1, "Categoría no encontrada", null)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new jsonResult(-1, "Error", e.getMessage())).build();
        }
    }
}
