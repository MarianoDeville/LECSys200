-- MySQL Script generated by MySQL Workbench
-- Sat Dec  2 17:57:25 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema LECSys2.00
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema LECSys2.00
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `LECSys2.00` DEFAULT CHARACTER SET utf8 ;
USE `LECSys2.00` ;

-- -----------------------------------------------------
-- Table `LECSys2.00`.`alumnos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`alumnos` (
  `legajo` INT NOT NULL AUTO_INCREMENT,
  `idCurso` INT NOT NULL,
  `dni` INT NULL,
  `fechaIngreso` DATE NOT NULL,
  `estado` INT NOT NULL,
  `idGrupoFamiliar` INT NULL,
  `fechaBaja` DATE NULL,
  PRIMARY KEY (`legajo`),
  INDEX `idCurso_idx` (`idCurso` ASC) VISIBLE,
  INDEX `idGrupoFamiliar_idx` (`idGrupoFamiliar` ASC) VISIBLE,
  INDEX `dniPersona_idx` (`dni` ASC) VISIBLE,
  CONSTRAINT `idCurso`
    FOREIGN KEY (`idCurso`)
    REFERENCES `LECSys2.00`.`curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idGrupoFamiliar`
    FOREIGN KEY (`idGrupoFamiliar`)
    REFERENCES `LECSys2.00`.`grupoFamiliar` (`idGrupoFamiliar`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `dniPersona`
    FOREIGN KEY (`dni`)
    REFERENCES `LECSys2.00`.`persona` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`cobros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`cobros` (
  `idCobros` INT NOT NULL AUTO_INCREMENT,
  `idGrupoFamiliar` INT NOT NULL,
  `nombre` VARCHAR(60) NOT NULL,
  `concepto` VARCHAR(180) NOT NULL,
  `fecha` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `monto` DOUBLE NOT NULL,
  `factura` VARCHAR(20) NULL,
  `idEstadisticas` INT NULL,
  PRIMARY KEY (`idCobros`),
  INDEX `idGrupoFamiliar_idx` (`idGrupoFamiliar` ASC) VISIBLE,
  INDEX `estadisticas_idx` (`idEstadisticas` ASC) VISIBLE,
  CONSTRAINT `idGrupoFamilia`
    FOREIGN KEY (`idGrupoFamiliar`)
    REFERENCES `LECSys2.00`.`grupoFamiliar` (`idGrupoFamiliar`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `estadisticas`
    FOREIGN KEY (`idEstadisticas`)
    REFERENCES `LECSys2.00`.`estadísticas` (`idEstadísticas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`contacto` (
  `idcontacto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `teléfono` VARCHAR(20) NULL,
  `email` VARCHAR(40) NULL,
  `idProveedores` INT NOT NULL,
  `sector` VARCHAR(90) NULL,
  PRIMARY KEY (`idcontacto`),
  INDEX `idProveedor_idx` (`idProveedores` ASC) VISIBLE,
  CONSTRAINT `proveedor`
    FOREIGN KEY (`idProveedores`)
    REFERENCES `LECSys2.00`.`proveedores` (`idProveedores`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`cotizaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`cotizaciones` (
  `idCotizaciones` INT NOT NULL AUTO_INCREMENT,
  `precio` DOUBLE NULL,
  `idInsumo` INT NULL,
  `idPresupuesto` INT NULL,
  PRIMARY KEY (`idCotizaciones`),
  INDEX `idPesupuesto_idx` (`idPresupuesto` ASC) VISIBLE,
  INDEX `insumo_idx` (`idInsumo` ASC) VISIBLE,
  CONSTRAINT `insumo`
    FOREIGN KEY (`idInsumo`)
    REFERENCES `LECSys2.00`.`insumos` (`idInsumos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idPesupuesto`
    FOREIGN KEY (`idPresupuesto`)
    REFERENCES `LECSys2.00`.`presupuesto` (`idPresupuesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`curso` (
  `idCurso` INT NOT NULL AUTO_INCREMENT,
  `año` VARCHAR(15) NOT NULL,
  `nivel` VARCHAR(15) NOT NULL,
  `idProfesor` INT NOT NULL,
  `estado` INT NOT NULL,
  `aula` INT NOT NULL,
  PRIMARY KEY (`idCurso`),
  INDEX `idProfesor_idx` (`idProfesor` ASC) VISIBLE,
  CONSTRAINT `idProfesor`
    FOREIGN KEY (`idProfesor`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`empleados` (
  `legajo` INT NOT NULL AUTO_INCREMENT,
  `dni` INT NULL,
  `sueldo` DOUBLE NULL,
  `fechaIngreso` DATE NOT NULL,
  `estado` INT NOT NULL,
  `sector` VARCHAR(45) NOT NULL,
  `cargo` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NULL,
  `fechaBaja` DATE NULL,
  PRIMARY KEY (`legajo`),
  INDEX `persona_dni` (`dni` ASC) INVISIBLE,
  CONSTRAINT `persona`
    FOREIGN KEY (`dni`)
    REFERENCES `LECSys2.00`.`persona` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`estadísticas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`estadísticas` (
  `idEstadísticas` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `estudiantesActivos` INT NULL,
  `empleados` INT NULL,
  `ingresosMes` DOUBLE NULL,
  `sueldos` DOUBLE NULL,
  `compras` DOUBLE NULL,
  `servicios` DOUBLE NULL,
  `faltasEmpleados` INT NULL,
  `faltasAlumnos` INT NULL,
  PRIMARY KEY (`idEstadísticas`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`examenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`examenes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idAlumno` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `tipo` VARCHAR(25) NOT NULL,
  `nota` INT NOT NULL,
  `idProfesor` INT NOT NULL,
  `idCurso` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `alumno_idx` (`idAlumno` ASC) VISIBLE,
  INDEX `profesor_idx` (`idProfesor` ASC) VISIBLE,
  INDEX `curso_idx` (`idCurso` ASC) VISIBLE,
  CONSTRAINT `alumno`
    FOREIGN KEY (`idAlumno`)
    REFERENCES `LECSys2.00`.`alumnos` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `profesor`
    FOREIGN KEY (`idProfesor`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `curso`
    FOREIGN KEY (`idCurso`)
    REFERENCES `LECSys2.00`.`curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`faltas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`faltas` (
  `idFaltas` INT NOT NULL AUTO_INCREMENT,
  `idAlumnos` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `estado` INT NOT NULL,
  `idCurso` INT NOT NULL,
  PRIMARY KEY (`idFaltas`),
  INDEX `idAlumnos_idx` (`idAlumnos` ASC) VISIBLE,
  INDEX `idCurso_idx` (`idCurso` ASC) VISIBLE,
  CONSTRAINT `idAlumnos`
    FOREIGN KEY (`idAlumnos`)
    REFERENCES `LECSys2.00`.`alumnos` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idCursoFaltas`
    FOREIGN KEY (`idCurso`)
    REFERENCES `LECSys2.00`.`curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`grupoFamiliar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`grupoFamiliar` (
  `idGrupoFamiliar` INT NOT NULL AUTO_INCREMENT,
  `nombreFamilia` VARCHAR(60) NOT NULL,
  `deuda` INT NULL DEFAULT 0,
  `estado` INT NOT NULL DEFAULT 1,
  `descuento` INT NULL,
  `email` VARCHAR(90) NULL,
  PRIMARY KEY (`idGrupoFamiliar`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`horarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`horarios` (
  `idHorarios` INT NOT NULL AUTO_INCREMENT,
  `día` INT NOT NULL,
  `hora` TIME NOT NULL,
  `duración` INT NOT NULL,
  `idPertenece` INT NOT NULL,
  `granularidad` INT NOT NULL,
  `idCurso` INT NULL,
  PRIMARY KEY (`idHorarios`),
  INDEX `empleado_idx` (`idPertenece` ASC) VISIBLE,
  INDEX `curso_idx` (`idCurso` ASC) VISIBLE,
  CONSTRAINT `empleadosId`
    FOREIGN KEY (`idPertenece`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cursoId`
    FOREIGN KEY (`idCurso`)
    REFERENCES `LECSys2.00`.`curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`insumos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`insumos` (
  `idInsumos` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NULL,
  `descripción` VARCHAR(120) NULL,
  `presentación` VARCHAR(60) NULL,
  `estado` INT NULL,
  `cant` INT NULL,
  PRIMARY KEY (`idInsumos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`ordenCompra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`ordenCompra` (
  `idOrdenCompra` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NULL,
  `autoriza` INT NULL,
  `idPresupuesto` INT NULL,
  `idPago` INT NULL,
  PRIMARY KEY (`idOrdenCompra`),
  INDEX `presupuesto_idx` (`idPresupuesto` ASC) VISIBLE,
  INDEX `idPago_idx` (`idPago` ASC) VISIBLE,
  INDEX `autoriza_idx` (`autoriza` ASC) VISIBLE,
  CONSTRAINT `presupuesto`
    FOREIGN KEY (`idPresupuesto`)
    REFERENCES `LECSys2.00`.`presupuesto` (`idPresupuesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idPago`
    FOREIGN KEY (`idPago`)
    REFERENCES `LECSys2.00`.`pagos` (`idPagos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `autoriza`
    FOREIGN KEY (`autoriza`)
    REFERENCES `LECSys2.00`.`usuarios` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`pagos` (
  `idPagos` INT NOT NULL AUTO_INCREMENT,
  `idEmpleados` INT NULL,
  `idProveedor` INT NULL,
  `idEstadisticas` INT NULL,
  `concepto` VARCHAR(180) NOT NULL,
  `fecha` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `monto` DOUBLE NOT NULL,
  `factura` VARCHAR(20) NULL,
  `comentario` VARCHAR(180) NULL,
  `formaPago` VARCHAR(100) NULL,
  PRIMARY KEY (`idPagos`),
  INDEX `idProfesores_idx` (`idEmpleados` ASC) VISIBLE,
  INDEX `idEstadisticas_idx` (`idEstadisticas` ASC) VISIBLE,
  INDEX `idProveedor_idx` (`idProveedor` ASC) VISIBLE,
  CONSTRAINT `idEmpleado`
    FOREIGN KEY (`idEmpleados`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idEstadisticas`
    FOREIGN KEY (`idEstadisticas`)
    REFERENCES `LECSys2.00`.`estadísticas` (`idEstadísticas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idProveedor`
    FOREIGN KEY (`idProveedor`)
    REFERENCES `LECSys2.00`.`proveedores` (`idProveedores`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`pedido` (
  `idPedido` INT NOT NULL AUTO_INCREMENT,
  `idInsumo` INT NULL,
  `idSolicitud` INT NULL,
  `cant` INT NULL,
  PRIMARY KEY (`idPedido`),
  INDEX `insumos_idx` (`idInsumo` ASC) VISIBLE,
  INDEX `solicitud_idx` (`idSolicitud` ASC) VISIBLE,
  CONSTRAINT `insumos`
    FOREIGN KEY (`idInsumo`)
    REFERENCES `LECSys2.00`.`insumos` (`idInsumos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `solicitud`
    FOREIGN KEY (`idSolicitud`)
    REFERENCES `LECSys2.00`.`pedidoCompra` (`idPedidoCompra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`pedidoCompra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`pedidoCompra` (
  `idPedidoCompra` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NULL,
  `sectorSolicitante` VARCHAR(45) NULL,
  `estado` INT NULL,
  `idSolicitante` INT NULL,
  PRIMARY KEY (`idPedidoCompra`),
  INDEX `solicitanta_idx` (`idSolicitante` ASC) VISIBLE,
  CONSTRAINT `solicitanta`
    FOREIGN KEY (`idSolicitante`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`persona` (
  `dni` INT NOT NULL,
  `nombre` VARCHAR(30) NOT NULL,
  `apellido` VARCHAR(30) NOT NULL,
  `dirección` VARCHAR(45) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `teléfono` VARCHAR(20) NOT NULL,
  `email` VARCHAR(40) NULL,
  INDEX `dni` (`dni` ASC) VISIBLE,
  PRIMARY KEY (`dni`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`presupuesto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`presupuesto` (
  `idPresupuesto` INT NOT NULL AUTO_INCREMENT,
  `idPedidoCompra` INT NULL,
  `fecha` DATE NULL,
  `idProveedor` INT NOT NULL,
  `validez` DATE NULL,
  `estado` INT NULL,
  PRIMARY KEY (`idPresupuesto`),
  INDEX `pedidocompra_idx` (`idPedidoCompra` ASC) VISIBLE,
  INDEX `proveedor_idx` (`idProveedor` ASC) VISIBLE,
  CONSTRAINT `pedidocompra`
    FOREIGN KEY (`idPedidoCompra`)
    REFERENCES `LECSys2.00`.`pedidoCompra` (`idPedidoCompra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `proveedores`
    FOREIGN KEY (`idProveedor`)
    REFERENCES `LECSys2.00`.`proveedores` (`idProveedores`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`proveedores` (
  `idProveedores` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(90) NULL,
  `direccion` VARCHAR(90) NULL,
  `cuit` VARCHAR(20) NULL,
  `tipo` VARCHAR(45) NULL,
  `estado` INT NULL,
  PRIMARY KEY (`idProveedores`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`puntualidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`puntualidad` (
  `idPuntualidad` INT NOT NULL AUTO_INCREMENT,
  `idEmpleado` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `horaEntrada` TIME NULL,
  `horaSalida` TIME NULL,
  `comentario` VARCHAR(90) NULL,
  PRIMARY KEY (`idPuntualidad`),
  INDEX `empleado_id` (`idEmpleado` ASC) VISIBLE,
  CONSTRAINT `empleados`
    FOREIGN KEY (`idEmpleado`)
    REFERENCES `LECSys2.00`.`empleados` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`registroActividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`registroActividad` (
  `idRegistroActividad` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `acción` VARCHAR(100) NOT NULL,
  `modulo` VARCHAR(50) NOT NULL,
  `ip` VARCHAR(20) NOT NULL,
  `tiempo` INT NULL,
  PRIMARY KEY (`idRegistroActividad`),
  INDEX `usr_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `idUsuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `LECSys2.00`.`usuarios` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`usuarios` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `dni` INT NOT NULL,
  `nombre` VARCHAR(20) NOT NULL,
  `contraseña` VARCHAR(90) NOT NULL,
  `nivelAcceso` VARCHAR(12) NOT NULL,
  `estado` INT NOT NULL,
  `cambioContraseña` INT NULL,
  PRIMARY KEY (`idUsuario`),
  INDEX `empleado_idx` (`dni` ASC) VISIBLE,
  CONSTRAINT `empleado`
    FOREIGN KEY (`dni`)
    REFERENCES `LECSys2.00`.`empleados` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LECSys2.00`.`valorCuota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LECSys2.00`.`valorCuota` (
  `idValorCuota` INT NOT NULL AUTO_INCREMENT,
  `idCurso` INT NOT NULL,
  `precio` DOUBLE NOT NULL,
  PRIMARY KEY (`idValorCuota`),
  INDEX `fk_valorCuota_curso1_idx` (`idCurso` ASC) VISIBLE,
  CONSTRAINT `idCurso1`
    FOREIGN KEY (`idCurso`)
    REFERENCES `LECSys2.00`.`curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
