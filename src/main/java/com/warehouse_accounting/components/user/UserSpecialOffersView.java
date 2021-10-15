package com.warehouse_accounting.components.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.warehouse_accounting.components.AppView;


@PageTitle("Спецпредложения")
@Route(value = "specialOffers", layout = AppView.class)
public class UserSpecialOffersView extends VerticalLayout {


    public UserSpecialOffersView() {

        add(new H1("Предложения от наших партнеров"));

        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        layout.add(tochkaBankOffers());
        layout.add(tinkoffBankOffers());
        layout.add(alfaBankOffers());
        layout.add(netHouseOffers());
        add(layout);




    }

    private VerticalLayout alfaBankOffers(){
        VerticalLayout verticalLayout = new VerticalLayout();
        String alfaInfo =
                "Спецусловия на тарифе «Просто 1%» " +
                        "• Платите только 1% от входящих поступлений на счет — всё остальное бесплатно:\n" +
                        "• Открытие и обслуживание счета и карты — 0 ₽\n" +
                        "• Снятие наличных по карте Альфа-Бизнес — 0 ₽\n" +
                        "• Платежи и все другие операции по счету — 0 ₽";

        Image alfaBankImage = new Image("specialOffers/alfaBank.png","Image not found");
        TextArea textArea = new TextArea();
        textArea.setWidth("330px");
        textArea.setLabel("Бaнк");
        textArea.setValue(alfaInfo);
        textArea.setReadOnly(true);


        Button alfaButton = new Button("Открыть счет");
        alfaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        alfaButton.click();
        String alfaBankOffers = "https://alfabank.ru/sme/?code=moysklad&PartnerPin=UAQZQB";
        Anchor anchor = new Anchor(alfaBankOffers, alfaButton);
        anchor.setTarget("href");
        verticalLayout.add(alfaBankImage);
        verticalLayout.add(textArea);
        verticalLayout.add(anchor);

        return verticalLayout;
    }

    private VerticalLayout tinkoffBankOffers() {
        VerticalLayout verticalLayout = new VerticalLayout();
        String tinkoffInfo = "• Расчетный счет — 0 ₽ открытие\n" +
                             "• Все онлайн\n" +
                             "• До 3% на остаток по счету\n" +
                             "• До 220 000 ₽ на сервисы партнеров";

        Image tinkoffImage = new Image("specialOffers/tinkoff.png","Image not found");
        TextArea textArea = new TextArea();
        textArea.setWidth("330px");
        textArea.setLabel("Бaнк");
        textArea.setValue(tinkoffInfo);
        textArea.setReadOnly(true);

        Button tinkoffButton = new Button("Открыть счет");
        tinkoffButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        tinkoffButton.click();

        String tinkoffOffers = "https://www.tinkoff.ru/business/?utm_source=partner_rko_sme&utm_medium=ptr.act&utm_campaign=sme.partners&partnerId=5-2OEQYUE3#form-application";
        Anchor anchor = new Anchor(tinkoffOffers, tinkoffButton);
        anchor.setTarget("href");
        verticalLayout.add(tinkoffImage);
        verticalLayout.add(textArea);
        verticalLayout.add(anchor);

        return  verticalLayout;
    }

    private  VerticalLayout tochkaBankOffers() {
        VerticalLayout verticalLayout = new VerticalLayout();
        String tochkaInfo = "Освободим ваше время: все счета, карты, " +
                "балансы и бухгалтерию можно посмотреть\n" +
                "в интернет-банке. " +
                "Доступно на любом устройстве и на одном экране. " +
                "Не оставим в одиночестве: отвечаем в чате и по телефону 24/7.";

        Image tochkaImage = new Image("specialOffers/tochka.png","Image not found");
        TextArea textArea = new TextArea();
        textArea.setWidth("330px");
        textArea.setLabel("Бaнк");
        textArea.setValue(tochkaInfo);
        textArea.setReadOnly(true);

        Button tochkaButton = new Button("Открыть счет");
        tochkaButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
        tochkaButton.click();

        String tochkaOffers = "https://partner.tochka.com/?referer1=myskladLK";
        Anchor anchor = new Anchor(tochkaOffers, tochkaButton);
        anchor.setTarget("href");
        verticalLayout.add(tochkaImage);
        verticalLayout.add(textArea);
        verticalLayout.add(anchor);

        return  verticalLayout;
    }

    private  VerticalLayout netHouseOffers(){
        VerticalLayout verticalLayout = new VerticalLayout();
        String nHouseInfo = "Создайте интернет-магазин в удобном конструкторе сайтов Nethouse. " +
                "Настройте автоматический перенос заказов в МойСклад," +
                " а обратно будут передаваться товарные остатки.\n" +
                "Получите 2 месяца тарифа «Магазин» в подарок после регистрации.";

        Image nHouseImage = new Image("specialOffers/netHouse.png","Image not found");
        TextArea textArea = new TextArea();
        textArea.setWidth("330px");
        textArea.setLabel("Конструктор сайта");
        textArea.setValue(nHouseInfo);
        textArea.setReadOnly(true);

        Button nHouseButton = new Button("Создать сайт");
        nHouseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nHouseButton.click();

        String nHouseOffers = "https://nethouse.ru/?p=moysklad2015";
        Anchor anchor = new Anchor(nHouseOffers, nHouseButton);
        anchor.setTarget("href");
        verticalLayout.add(nHouseImage);
        verticalLayout.add(textArea);
        verticalLayout.add(anchor);

        return  verticalLayout;

    }
}
