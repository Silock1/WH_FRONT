package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

@CssImport(value = "./css/application.css")
@Getter
@Setter
public class BonusTransactionForm extends VerticalLayout {
    private Button closedButton;
    private TypeOperation typeOperation;

    public BonusTransactionForm(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;

        setSizeFull();
        setVisible(false);

        add(buttonLine(),
                titleLine(),
                checkBoxLine(),
                pointsLine(),
                headerCommentLine(),
                commentLine(),
                taskButtonLine(),
                filesButtonLine(),
                filesTableLine()

        );
    }


    public HorizontalLayout buttonLine() {
        HorizontalLayout l = new HorizontalLayout();

        l.add(
                new Button("Сохранить"),
                closedButton = new Button("Закрыть"),
                new Button("Изменить")
        );
        return l;

    }

    public HorizontalLayout titleLine() {
        HorizontalLayout l = new HorizontalLayout();

        l.add(
                new Span(String.format("%s бонусных баллов №", typeOperation.getValue())),
                new Input(),
                new Span("от"),
                new Input()

        );

        return l;

    }

    public HorizontalLayout checkBoxLine() {
        HorizontalLayout l = new HorizontalLayout();

        ComboBox bonusProgram = new ComboBox();
        bonusProgram.setItems("Program1", "Program2", "Program3");

        ComboBox contrAgent = new ComboBox();
        contrAgent.setItems("Contr1", "Contr2", "Contr3");

        l.add(
                new Span("Бонусная программа"),
                bonusProgram,
                new Icon(VaadinIcon.PENCIL),
                new Span("Контрагент"),
                contrAgent
        );


        return l;

    }

    public HorizontalLayout pointsLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span(typeOperation.getValue()),
                new Input(),
                new Span("баллов"),
                new Span(typeOperation.dateType()),
                new Input()
        );
        return l;

    }

    public HorizontalLayout headerCommentLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span("Главная"),
                new Span("Связанные документы")
        );
        return l;

    }

    public HorizontalLayout commentLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(new Input());
        return l;

    }

    public HorizontalLayout taskButtonLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span("Задачи"),
                new Input());
        return l;

    }

    public HorizontalLayout filesButtonLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span("Файлы"),
                new Input());
        return l;

    }

    public HorizontalLayout filesTableLine() {

        HorizontalLayout l = new HorizontalLayout();


        l.add(new Span("IM GRID"));
        return l;

    }

    public enum TypeOperation {
        CHARGE("Начисление"), WRITE_OFF("Списание");
        private String type;

        TypeOperation(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }

        public String dateType() {
            if (this == CHARGE) {
                return "Дата начислен" +
                        "ия";
            } else {
                return "Дата списания";
            }
        }


    }


}
