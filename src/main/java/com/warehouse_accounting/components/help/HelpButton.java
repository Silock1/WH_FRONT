package com.warehouse_accounting.components.help;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.StreamResource;
import com.warehouse_accounting.components.AppView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CssImport(value = "./css/helpBlock.css")
public class HelpButton extends VerticalLayout {
    private boolean isVisible = false;
    private boolean bodyVisible = true;

    public HelpButton(Div parent) {
        parent.setWidth("0");
    }

    public boolean getIsVisible() { return isVisible; }

    public boolean helpDivVisible() {
        if (!isVisible) {
            isVisible = true;

        } else {
            isVisible = false;
        }

        return isVisible;
    }

    public Div helpDivInit() {
        Div helpContent = new Div();
        helpContent.addClassName("helpContent");
        helpContent.add(head(), managerDiv(), support(), foot());
        return helpContent;
    }

    private Div head() {
        Div head = new Div();
        Div headContent = new Div();

        head.addClassName("head");
        Div bar = new Div();
        bar.addClassName("bar");
        Span barText = new Span("С чего начать работу?");
        barText.addClassName("text1");
        barText.addClassName("text11");
        Div closeButton = new Div();
        closeButton.addClassName("closeButton");
        closeButton.addClickListener(divClickEvent -> {
            if (bodyVisible) {
                bodyVisible = false;
                headContent.setVisible(bodyVisible);
                closeButton.removeClassName("closeButton");
                closeButton.addClassName("closeButtonClosed");
            } else {
                bodyVisible = true;
                headContent.setVisible(bodyVisible);
                closeButton.removeClassName("closeButtonClosed");
                closeButton.addClassName("closeButton");
            }
        });
        bar.add(barText, closeButton);

        Div bodyText = new Div();
        bodyText.setText("Пройдите обучение и через пять минут вы узнаете: как добавить товары, клиентов, " +
                "как учитывать остатки, печатать документы и смотреть прибыль от продаж.");
        bodyText.addClassName("text2");

        Button startBtn = new Button("Начать обучение");
        startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        startBtn.addClassName("startBtn");

        headContent.add(bodyText, startBtn, lessonTubs());
        head.add(bar, headContent);

        return head;
    }

    private Div lessonTubs() {
        Div tabs = new Div();
        tabs.addClassName("tabs");
        List<String> tabNames = Arrays.asList("Реквизиты", "Товары", "Поступление", "Остатки", "Отгрузка", "Прибыль");
        List<InputStream> icons = Stream.of(AppView.getImageInputStream("helpBar1.png"),
                AppView.getImageInputStream("helpBar2.png"),
                AppView.getImageInputStream("helpBar3.png"),
                AppView.getImageInputStream("helpBar4.png"),
                AppView.getImageInputStream("helpBar5.png"),
                AppView.getImageInputStream("helpBar6.png")).collect(Collectors.toList());

        for(int i = 0; i < icons.size(); i++) {
            final int lambda = i;
            StreamResource res = new StreamResource("HelpBarIcon",
                    () -> icons.get(lambda));
            Image icon = new Image(res, "HelpBarIcon");
            icon.addClassName("icon");
            Span text = new Span(tabNames.get(i));
            text.addClassName("text");
            VerticalLayout layout = new VerticalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.add(icon, text);
            Span backLayer = new Span("Для начала этого урока необходимо пройти предыдущий");
            backLayer.addClassName("backLayer");
            backLayer.addClassName("backLayerFocused"); // if (предыдущий урок не пройден)
            Div tab = new Div(); // eventButton
            tab.addClassName("managerTab");
            tab.add(backLayer, layout);
            tabs.add(tab);
        }
        return tabs;
    }
    private Div managerDiv() {
        Div content = new Div();
        content.addClassName("managerDiv");

        Span head = new Span("Персональный менеджер");
        head.addClassName("text1");

        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("managerLayout");

        StreamResource res = new StreamResource("helpAvatar.jpeg",
                () -> AppView.getImageInputStream("helpAvatar.jpeg"));
        Image avatar = new Image(res, "HelpAvatar");
        avatar.addClassName("avatarStyle");

        Div contactInfo = new Div();
        contactInfo.addClassName("contactInfo");
        Span name = new Span("Ольга Сидорова");
        name.addClassName("name");

        HorizontalLayout phoneLayout = new HorizontalLayout();
        phoneLayout.addClassName("phoneLayout");
        Span phone = new Span("Телефон:");
        phone.addClassName("anotherInfo");
        Span phoneValue = new Span("8 800 250-04-32 доб. 162");
        phoneValue.addClassName("anotherInfoValue");
        phoneLayout.add(phone, phoneValue);

        HorizontalLayout mailLayout = new HorizontalLayout();
        Span mail = new Span("E-mail:");
        mail.addClassName("anotherInfo");
        Span mailValue = new Span("osidorova@moysklad.ru");
        mailValue.addClassName("anotherInfoValue");
        mailLayout.add(mail, mailValue);

        HorizontalLayout skypeLayout = new HorizontalLayout();
        Span skype = new Span("Skype:");
        skype.addClassName("anotherInfo");
        Span skypeValue = new Span("Olga_moysklad");
        skypeValue.addClassName("anotherInfoValue");
        skypeLayout.add(skype, skypeValue);

        Div text = new Div();
        text.setText("Наш специалист ответит на ваши вопросы! " +
                "Позвоните по указанным номерам, чтобы получить консультацию");
        text.addClassName("anotherInfo1");

        contactInfo.add(name,phoneLayout, mailLayout, skypeLayout, text);
        layout.add(avatar, contactInfo);

        content.add(head, layout);
        return content;
    }

    private Div support() {
        Div content = new Div();
        content.addClassName("head");

        Div head = new Div();
        head.setText("Поддержка");
        head.addClassName("text1");

        Span text = new Span("Ответы на вопросы вы можете получить в разделе «Документация» или у нашего " +
                "специалиста. Если вам требуется дополнительный курс обучения — запишитесь " +
                "на наш вебинар.");
        text.addClassName("anotherInfo1");

        Div buttons = new Div();
        buttons.addClassName("supportButtons");

        Button button1 = new Button("Чат со специалистом");
        button1.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button1.addClassName("supportButton1");

        Button button2 = new Button("Вебинар");
        button2.addClassName("button2");
        Div iconBtn2 = new Div();
        iconBtn2.addClassName("supportIcon2");
        Div btn2 = new Div();
        btn2.add(button2, iconBtn2);

        Button button3 = new Button("Документация");
        button3.addClassName("button2");
        Div iconBtn3 = new Div();
        iconBtn3.addClassName("supportIcon2");
        iconBtn3.addClassName("supportIcon3");
        Div btn3 = new Div();
        btn3.add(button3, iconBtn3);

        buttons.add(button1, btn2, btn3);

        Checkbox checkBox = new Checkbox();
        checkBox.setLabel("Доступ для сотрудников поддержки");

        content.add(head, text, buttons, checkBox);
        return content;
    }

    private Div foot() {
        Div content = new Div();
        content.addClassName("managerDiv");
        content.addClassName("flex");

        Span formDocs = new Span("Формы документов");
        formDocs.addClassName("formDocs");

        Checkbox checkBox = new Checkbox();
        checkBox.setLabel("Показывать при старте");
        checkBox.addClassName("footCheckBox");

        content.add(formDocs, checkBox);
        return content;
    }
}
