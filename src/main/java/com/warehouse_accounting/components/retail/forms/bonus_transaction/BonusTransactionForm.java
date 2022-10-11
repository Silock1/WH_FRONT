package com.warehouse_accounting.components.retail.forms.bonus_transaction;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.models.dto.BonusProgramDto;
import com.warehouse_accounting.models.dto.ContractorDto;
import lombok.Getter;
import lombok.Setter;


@CssImport(value = "./css/application.css")
@Getter
@Setter
public class BonusTransactionForm extends VerticalLayout {
    private Button closedButton;
    private TypeOperation typeOperation;
    private Button saveButton;
    private IntegerField idInput;
    private IntegerField bonusValueInput;
    private ComboBox<BonusProgramDto> bonusProgram;
    private ComboBox<ContractorDto> contractor;
    private TextField comment;
    private DatePicker executionDate;
    private DatePicker createdDate;

    public BonusTransactionForm(TypeOperation typeOperation) {

        this.typeOperation = typeOperation;
        setSizeFull();
        setVisible(false);


        add(
                buttonLine(),
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
                saveButton = new Button("Сохранить"),
                closedButton = new Button("Закрыть"),
                new Button("Изменить")//Menubar удалить копировать ЗАГЛУШКА
        );
        return l;

    }

    public HorizontalLayout titleLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span(String.format("%s бонусных баллов №", typeOperation.getValue())),
                idInput = new IntegerField(),//id
                new Span("от"),
                createdDate = new DatePicker()//created

        );

        return l;

    }

    public HorizontalLayout checkBoxLine() {
        HorizontalLayout l = new HorizontalLayout();

        bonusProgram = new ComboBox<>();


        contractor = new ComboBox<>();


        l.add(
                new Span("Бонусная программа"),
                bonusProgram,//combobox bonusProgram
                new Icon(VaadinIcon.PENCIL),
                new Span("Контрагент"),
                contractor//combobox contrAgent
        );


        return l;

    }

    public HorizontalLayout pointsLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span(typeOperation.getValue()),
                bonusValueInput = new IntegerField(),//bonusValue
                new Span("баллов"),
                new Span(typeOperation.dateType()),
                executionDate = new DatePicker()//executionDate дата начисления/списания
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


        l.add(
                comment = new TextField()
        );//comment
        return l;

    }

    public HorizontalLayout taskButtonLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span("Задачи"),
                new Button("Задачи"));
        return l;

    }

    public HorizontalLayout filesButtonLine() {
        HorizontalLayout l = new HorizontalLayout();


        l.add(
                new Span("Файлы"),
                new Button("Файлы"));
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
