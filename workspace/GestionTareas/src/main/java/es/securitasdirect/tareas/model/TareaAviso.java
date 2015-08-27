package es.securitasdirect.tareas.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Tareas de tipo Aviso
 */
public class TareaAviso extends Tarea {

	/**
	 * Identificador de Aviso, no confundir con Id Tarea
	 */
	Integer idAviso;
	/**
	 * Tipos de Aviso: el primero es el utilizado, el segundo y el tercero son sólo informativos.
	 */
	String tipoAviso1;
	String tipoAviso2;
	String tipoAviso3;
	/**
	 * Motivos: el primero es el utilizado, el segundo y el tercero son sólo informativos.
	 */
	String motivo1;
	String motivo2;
	String motivo3;
	/*
    Observaciones: información adicional
	 */
	String observaciones;
	/**
	 * Identificativo Aviso-Tarea: conexión entre el Aviso y la Tarea (para poder seguir la trazabilidad entre ambos aplicativos)
	 */
	Integer identificativoAvisoTarea;
	/**
	 * Identificativo Instalación: número de instalación/cliente
	 */
	String numeroInstalacion;
	/*	Titular: nombre del cliente, a modo informativo  */
	String titular;
	/*	Requerido por: importante para el reporting y para algunos departamentos  */
	String requeridoPor;
	/*	Datos de Contacto: cliente que inició la incidencia, forma de contacto, horarios de contacto  */
	String datosContacto;
	/*	Fecha de creación: información para trazabilidad y para la gestión de días de vencimiento  */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	Date fechaCreacion;
	/*	Estado  */
	String estado;
	/** Horario desde */
	private String horarioDesde;
	/** Horario hasta */
	private String horarioHasta;
	/**
	 * Motivo de Cierre, tipo cadena por ejemplo 'ILOC'
	 */
	String closing;

	/**
	 * Datos adicionales del cierre. TODO Pasarlo a integer??
	 */
	String datosAdicionalesCierre;
	/**
	 * Fecha de Cierre
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	Date fechaCierre;
	/**
	 * Nota
	 */
	String nota;
	/**
	 * Responsable del cierre
	 */
	String responsableCierre;

    /**
     * Telefono de la Tarea de Aviso, que puede ser distinto al telefono de la Tarea
     */
    private String telefonoAviso;

	public String getHorarioDesde() {
		return horarioDesde;
	}

	public void setHorarioDesde(String horarioDesde) {
		this.horarioDesde = horarioDesde;
	}

	public String getHorarioHasta() {
		return horarioHasta;
	}

	public void setHorarioHasta(String horarioHasta) {
		this.horarioHasta = horarioHasta;
	}

	public String getTipoAviso1() {
		return tipoAviso1;
	}

	public void setTipoAviso1(String tipoAviso1) {
		this.tipoAviso1 = tipoAviso1;
	}

	public String getTipoAviso2() {
		return tipoAviso2;
	}

	public void setTipoAviso2(String tipoAviso2) {
		this.tipoAviso2 = tipoAviso2;
	}

	public String getTipoAviso3() {
		return tipoAviso3;
	}

	public void setTipoAviso3(String tipoAviso3) {
		this.tipoAviso3 = tipoAviso3;
	}

	public String getMotivo1() {
		return motivo1;
	}

	public void setMotivo1(String motivo1) {
		this.motivo1 = motivo1;
	}

	public String getMotivo2() {
		return motivo2;
	}

	public void setMotivo2(String motivo2) {
		this.motivo2 = motivo2;
	}

	public String getMotivo3() {
		return motivo3;
	}

	public void setMotivo3(String motivo3) {
		this.motivo3 = motivo3;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdentificativoAvisoTarea() {
		return identificativoAvisoTarea;
	}

	public void setIdentificativoAvisoTarea(Integer identificativoAvisoTarea) {
		this.identificativoAvisoTarea = identificativoAvisoTarea;
	}

	public String getNumeroInstalacion() {
		return numeroInstalacion;
	}

	public void setNumeroInstalacion(String numeroInstalacion) {
		this.numeroInstalacion = numeroInstalacion;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getRequeridoPor() {
		return requeridoPor;
	}

	public void setRequeridoPor(String requeridoPor) {
		this.requeridoPor = requeridoPor;
	}

	public String getDatosContacto() {
		return datosContacto;
	}

	public void setDatosContacto(String datosContacto) {
		this.datosContacto = datosContacto;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getIdAviso() {
		return idAviso;
	}

	public void setIdAviso(Integer idAviso) {
		this.idAviso = idAviso;
	}

	public String getClosing() {
		return closing;
	}

	public void setClosing(String closing) {
		this.closing = closing;
	}

	public String getDatosAdicionalesCierre() {
		return datosAdicionalesCierre;
	}

	public void setDatosAdicionalesCierre(String datosAdicionalesCierre) {
		this.datosAdicionalesCierre = datosAdicionalesCierre;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getResponsableCierre() {
		return responsableCierre;
	}

	public void setResponsableCierre(String responsableCierre) {
		this.responsableCierre = responsableCierre;
	}

    public String getTelefonoAviso() {
        return telefonoAviso;
    }

    public void setTelefonoAviso(String telefonoAviso) {
        this.telefonoAviso = telefonoAviso;
    }

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TareaAviso{");
		sb.append("idAviso=").append(idAviso);
		sb.append(", tipoAviso1='").append(tipoAviso1).append('\'');
		sb.append(", tipoAviso2='").append(tipoAviso2).append('\'');
		sb.append(", tipoAviso3='").append(tipoAviso3).append('\'');
		sb.append(", motivo1='").append(motivo1).append('\'');
		sb.append(", motivo2='").append(motivo2).append('\'');
		sb.append(", motivo3='").append(motivo3).append('\'');
		sb.append(", observaciones='").append(observaciones).append('\'');
		sb.append(", identificativoAvisoTarea=").append(identificativoAvisoTarea);
		sb.append(", numeroInstalacion='").append(numeroInstalacion).append('\'');
		sb.append(", titular='").append(titular).append('\'');
		sb.append(", requeridoPor='").append(requeridoPor).append('\'');
		sb.append(", datosContacto='").append(datosContacto).append('\'');
		sb.append(", fechaCreacion=").append(fechaCreacion);
		sb.append(", estado='").append(estado).append('\'');
		sb.append(", horarioDesde='").append(horarioDesde).append('\'');
		sb.append(", horarioHasta='").append(horarioHasta).append('\'');
		sb.append(", closing=").append(closing);
		sb.append(", datosAdicionalesCierre=").append(datosAdicionalesCierre);
		sb.append(", fechaCierre=").append(fechaCierre);
		sb.append(", nota='").append(nota).append('\'');
		sb.append(", responsableCierre='").append(responsableCierre).append('\'');
        sb.append(", telefonoAviso='").append(telefonoAviso).append('\'');
		sb.append('}');
		return sb.toString();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TareaAviso that = (TareaAviso) o;

        if (closing != null ? !closing.equals(that.closing) : that.closing != null) return false;
        if (datosAdicionalesCierre != null ? !datosAdicionalesCierre.equals(that.datosAdicionalesCierre) : that.datosAdicionalesCierre != null)
            return false;
        if (datosContacto != null ? !datosContacto.equals(that.datosContacto) : that.datosContacto != null)
            return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (fechaCierre != null ? !fechaCierre.equals(that.fechaCierre) : that.fechaCierre != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (horarioDesde != null ? !horarioDesde.equals(that.horarioDesde) : that.horarioDesde != null) return false;
        if (horarioHasta != null ? !horarioHasta.equals(that.horarioHasta) : that.horarioHasta != null) return false;
        if (idAviso != null ? !idAviso.equals(that.idAviso) : that.idAviso != null) return false;
        if (identificativoAvisoTarea != null ? !identificativoAvisoTarea.equals(that.identificativoAvisoTarea) : that.identificativoAvisoTarea != null)
            return false;
        if (motivo1 != null ? !motivo1.equals(that.motivo1) : that.motivo1 != null) return false;
        if (motivo2 != null ? !motivo2.equals(that.motivo2) : that.motivo2 != null) return false;
        if (motivo3 != null ? !motivo3.equals(that.motivo3) : that.motivo3 != null) return false;
        if (nota != null ? !nota.equals(that.nota) : that.nota != null) return false;
        if (numeroInstalacion != null ? !numeroInstalacion.equals(that.numeroInstalacion) : that.numeroInstalacion != null)
            return false;
        if (observaciones != null ? !observaciones.equals(that.observaciones) : that.observaciones != null)
            return false;
        if (requeridoPor != null ? !requeridoPor.equals(that.requeridoPor) : that.requeridoPor != null) return false;
        if (responsableCierre != null ? !responsableCierre.equals(that.responsableCierre) : that.responsableCierre != null)
            return false;
        if (tipoAviso1 != null ? !tipoAviso1.equals(that.tipoAviso1) : that.tipoAviso1 != null) return false;
        if (tipoAviso2 != null ? !tipoAviso2.equals(that.tipoAviso2) : that.tipoAviso2 != null) return false;
        if (tipoAviso3 != null ? !tipoAviso3.equals(that.tipoAviso3) : that.tipoAviso3 != null) return false;
        if (titular != null ? !titular.equals(that.titular) : that.titular != null) return false;
        if (telefonoAviso != null ? !telefonoAviso.equals(that.telefonoAviso) : that.telefonoAviso != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (idAviso != null ? idAviso.hashCode() : 0);
        result = 31 * result + (tipoAviso1 != null ? tipoAviso1.hashCode() : 0);
        result = 31 * result + (tipoAviso2 != null ? tipoAviso2.hashCode() : 0);
        result = 31 * result + (tipoAviso3 != null ? tipoAviso3.hashCode() : 0);
        result = 31 * result + (motivo1 != null ? motivo1.hashCode() : 0);
        result = 31 * result + (motivo2 != null ? motivo2.hashCode() : 0);
        result = 31 * result + (motivo3 != null ? motivo3.hashCode() : 0);
        result = 31 * result + (observaciones != null ? observaciones.hashCode() : 0);
        result = 31 * result + (identificativoAvisoTarea != null ? identificativoAvisoTarea.hashCode() : 0);
        result = 31 * result + (numeroInstalacion != null ? numeroInstalacion.hashCode() : 0);
        result = 31 * result + (titular != null ? titular.hashCode() : 0);
        result = 31 * result + (requeridoPor != null ? requeridoPor.hashCode() : 0);
        result = 31 * result + (datosContacto != null ? datosContacto.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (horarioDesde != null ? horarioDesde.hashCode() : 0);
        result = 31 * result + (horarioHasta != null ? horarioHasta.hashCode() : 0);
        result = 31 * result + (closing != null ? closing.hashCode() : 0);
        result = 31 * result + (datosAdicionalesCierre != null ? datosAdicionalesCierre.hashCode() : 0);
        result = 31 * result + (fechaCierre != null ? fechaCierre.hashCode() : 0);
        result = 31 * result + (nota != null ? nota.hashCode() : 0);
        result = 31 * result + (responsableCierre != null ? responsableCierre.hashCode() : 0);
        result = 31 * result + (telefonoAviso != null ? telefonoAviso.hashCode() : 0);
        return result;
    }

    public boolean equalsSinDatosCierre(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TareaAviso other = (TareaAviso) obj;
		if (datosContacto == null) {
			if (other.datosContacto != null)
				return false;
		} else if (!datosContacto.equals(other.datosContacto))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaCierre == null) {
			if (other.fechaCierre != null)
				return false;
		} else if (!fechaCierre.equals(other.fechaCierre))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (horarioDesde == null) {
			if (other.horarioDesde != null)
				return false;
		} else if (!horarioDesde.equals(other.horarioDesde))
			return false;
		if (horarioHasta == null) {
			if (other.horarioHasta != null)
				return false;
		} else if (!horarioHasta.equals(other.horarioHasta))
			return false;
		if (idAviso == null) {
			if (other.idAviso != null)
				return false;
		} else if (!idAviso.equals(other.idAviso))
			return false;
		if (identificativoAvisoTarea == null) {
			if (other.identificativoAvisoTarea != null)
				return false;
		} else if (!identificativoAvisoTarea.equals(other.identificativoAvisoTarea))
			return false;
		if (motivo1 == null) {
			if (other.motivo1 != null)
				return false;
		} else if (!motivo1.equals(other.motivo1))
			return false;
		if (motivo2 == null) {
			if (other.motivo2 != null)
				return false;
		} else if (!motivo2.equals(other.motivo2))
			return false;
		if (motivo3 == null) {
			if (other.motivo3 != null)
				return false;
		} else if (!motivo3.equals(other.motivo3))
			return false;
		if (nota == null) {
			if (other.nota != null)
				return false;
		} else if (!nota.equals(other.nota))
			return false;
		if (numeroInstalacion == null) {
			if (other.numeroInstalacion != null)
				return false;
		} else if (!numeroInstalacion.equals(other.numeroInstalacion))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (requeridoPor == null) {
			if (other.requeridoPor != null)
				return false;
		} else if (!requeridoPor.equals(other.requeridoPor))
			return false;
		if (responsableCierre == null) {
			if (other.responsableCierre != null)
				return false;
		} else if (!responsableCierre.equals(other.responsableCierre))
			return false;
		if (tipoAviso1 == null) {
			if (other.tipoAviso1 != null)
				return false;
		} else if (!tipoAviso1.equals(other.tipoAviso1))
			return false;
		if (tipoAviso2 == null) {
			if (other.tipoAviso2 != null)
				return false;
		} else if (!tipoAviso2.equals(other.tipoAviso2))
			return false;
		if (tipoAviso3 == null) {
			if (other.tipoAviso3 != null)
				return false;
		} else if (!tipoAviso3.equals(other.tipoAviso3))
			return false;
		if (titular == null) {
            if (other.titular != null)
                return false;
        } else if (!titular.equals(other.titular))
            return false;
        if (telefonoAviso == null) {
            if (other.telefonoAviso != null)
                return false;
        } else if (!telefonoAviso.equals(other.telefonoAviso))
            return false;

		return true;
	}


    public boolean equalsConDatosCierre(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TareaAviso other = (TareaAviso) obj;
        if (datosContacto == null) {
            if (other.datosContacto != null)
                return false;
        } else if (!datosContacto.equals(other.datosContacto))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fechaCierre == null) {
            if (other.fechaCierre != null)
                return false;
        } else if (!fechaCierre.equals(other.fechaCierre))
            return false;
        if (fechaCreacion == null) {
            if (other.fechaCreacion != null)
                return false;
        } else if (!fechaCreacion.equals(other.fechaCreacion))
            return false;
        if (horarioDesde == null) {
            if (other.horarioDesde != null)
                return false;
        } else if (!horarioDesde.equals(other.horarioDesde))
            return false;
        if (horarioHasta == null) {
            if (other.horarioHasta != null)
                return false;
        } else if (!horarioHasta.equals(other.horarioHasta))
            return false;
        if (idAviso == null) {
            if (other.idAviso != null)
                return false;
        } else if (!idAviso.equals(other.idAviso))
            return false;
        if (identificativoAvisoTarea == null) {
            if (other.identificativoAvisoTarea != null)
                return false;
        } else if (!identificativoAvisoTarea.equals(other.identificativoAvisoTarea))
            return false;
        if (motivo1 == null) {
            if (other.motivo1 != null)
                return false;
        } else if (!motivo1.equals(other.motivo1))
            return false;
        if (motivo2 == null) {
            if (other.motivo2 != null)
                return false;
        } else if (!motivo2.equals(other.motivo2))
            return false;
        if (motivo3 == null) {
            if (other.motivo3 != null)
                return false;
        } else if (!motivo3.equals(other.motivo3))
            return false;
        if (nota == null) {
            if (other.nota != null)
                return false;
        } else if (!nota.equals(other.nota))
            return false;
        if (numeroInstalacion == null) {
            if (other.numeroInstalacion != null)
                return false;
        } else if (!numeroInstalacion.equals(other.numeroInstalacion))
            return false;
        if (observaciones == null) {
            if (other.observaciones != null)
                return false;
        } else if (!observaciones.equals(other.observaciones))
            return false;
        if (requeridoPor == null) {
            if (other.requeridoPor != null)
                return false;
        } else if (!requeridoPor.equals(other.requeridoPor))
            return false;
        if (responsableCierre == null) {
            if (other.responsableCierre != null)
                return false;
        } else if (!responsableCierre.equals(other.responsableCierre))
            return false;
        if (tipoAviso1 == null) {
            if (other.tipoAviso1 != null)
                return false;
        } else if (!tipoAviso1.equals(other.tipoAviso1))
            return false;
        if (tipoAviso2 == null) {
            if (other.tipoAviso2 != null)
                return false;
        } else if (!tipoAviso2.equals(other.tipoAviso2))
            return false;
        if (tipoAviso3 == null) {
            if (other.tipoAviso3 != null)
                return false;
        } else if (!tipoAviso3.equals(other.tipoAviso3))
            return false;
        if (titular == null) {
            if (other.titular != null)
                return false;
        } else if (!titular.equals(other.titular))
            return false;
        if (telefonoAviso == null) {
            if (other.telefonoAviso != null)
                return false;
        } else if (!telefonoAviso.equals(other.telefonoAviso))
            return false;
        if (closing == null) {
            if (other.closing != null)
                return false;
        } else if (!closing.equals(other.closing))
            return false;
        if (datosAdicionalesCierre == null) {
            if (other.datosAdicionalesCierre != null)
                return false;
        } else if (!datosAdicionalesCierre.equals(other.datosAdicionalesCierre))
            return false;

        return true;
    }

} 





