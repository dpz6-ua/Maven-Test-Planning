package ppss.P05;

public class AlquilaCochesTestable extends Alquilacoches{
    IService servicio;

    @Override
    public IService getServicio() {
        return servicio;
    }

    public void setServicio(IService servicio) {
        this.servicio = servicio;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
}
