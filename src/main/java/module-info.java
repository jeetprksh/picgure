module picgure {
  requires java.desktop;
  requires java.logging;
  requires com.fasterxml.jackson.annotation;
  requires java.compiler;
  requires org.hibernate.orm.core;
  requires java.persistence;
  requires commons.io;
  requires com.fasterxml.jackson.databind;
  requires java.sql;
  requires com.fasterxml.jackson.core;
  requires java.naming;
  requires java.xml.bind;
  requires com.formdev.flatlaf;

  opens com.picgure.persistence.dto to org.hibernate.orm.core;
}