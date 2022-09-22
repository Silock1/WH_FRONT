package com.warehouse_accounting.components.userSubMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.account.responses.GetProfileInfoResponse;
import com.vk.api.sdk.objects.groups.Filter;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import com.vk.api.sdk.objects.market.MarketItem;
import com.vk.api.sdk.objects.photos.responses.GetMarketUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MarketUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMarketPhotoResponse;
import com.warehouse_accounting.models.dto.ApplicationDto;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.services.interfaces.ApplicationService;
import com.warehouse_accounting.services.interfaces.ProductService;

import java.io.File;
import java.util.List;

@SpringComponent
@UIScope
public class FormForApplication extends VerticalLayout {

    private boolean newForm = false;
    private final Binder<ApplicationDto> applicationDtoBinder = new Binder<>(ApplicationDto.class);
    private final ApplicationService applicationService;
    private final ApplicationDto applicationDto = new ApplicationDto();
    private final ProductService productService;
    private ApplicationsList parent;
    boolean alreadyMakeUpdate = false;

    private UserActor actor;

    public FormForApplication(ApplicationService applicationService, ProductService productService) {
        this.applicationService = applicationService;
        this.productService = productService;
    }

    public void build(ApplicationDto applicationDto) {
        removeAll();
        newForm = false;
        applicationDtoBinder.readBean(applicationDto);
        add(createButton(applicationDto), startForm(applicationDto));
    }

    public void build() {
        removeAll();
        newForm = true;
//        if (alreadyMakeUpdate) {
//            try {
//                applicationDtoBinder.writeBean(applicationDto);
//            } catch (ValidationException e) {
//                throw new RuntimeException(e);
//            }
//        }
        add(createButton(applicationDto), enableAuthorization(applicationDto));
    }

    public void setParent(ApplicationsList parent) {
        this.parent = parent;
    }

    private HorizontalLayout createButton(ApplicationDto applicationDto) {
        HorizontalLayout layoutForButtons = new HorizontalLayout();
        layoutForButtons.setAlignItems(Alignment.CENTER);
        Button edit = new Button("Сохранить");
        if (newForm) {
            edit.setEnabled(false);
        }
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        edit.addClickListener(e -> {
            if (newForm) {
                try {
                    applicationDtoBinder.writeBean(applicationDto);
                    applicationService.create(applicationDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
//                    alreadyMakeUpdate = true;
                    applicationDtoBinder.writeBean(applicationDto);
                    applicationService.update(applicationDto);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            removeAll();
            parent.showButtonEndGrid(true);

        });

        Button close = new Button("Закрыть");
        close.addClickListener(e1 -> {
            removeAll();
            parent.showButtonEndGrid(false);
        });

        Button delete = new Button("Удалить настройку синхронизации (ВКонтакте)");
        if (newForm) {
            delete.setEnabled(false);
        }
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e1 -> {
            applicationService.deleteById(applicationDto.getId());
            build();
//            removeAll();
//            parent.showButtonEndGrid(false);
        });

        layoutForButtons.add(edit, close, delete);
        layoutForButtons.setAlignItems(Alignment.CENTER);
        return layoutForButtons;
    }

    private VerticalLayout startForm(ApplicationDto applicationDto) {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        actor = new UserActor(applicationDto.getVkUserId(), applicationDto.getVkAccessToken());

        GetProfileInfoResponse getProfileInfoResponse;
        try {
            getProfileInfoResponse = vk.account().getProfileInfo(actor).execute();
        } catch (ApiException | ClientException e) {
            applicationService.deleteById(applicationDto.getId()); //Временное решение, удаление магазина если ВК данные неверные
            throw new RuntimeException(e);
        }

        GetResponse getResponse;
        try {
            getResponse = vk.groups().get(actor).filter(Filter.ADMIN).execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        VerticalLayout verticalLayout = new VerticalLayout();

        TextField name = new TextField();
        TextField developer = new TextField();
        developer.setValue(getProfileInfoResponse.getFirstName() + " " + getProfileInfoResponse.getLastName());
        developer.setReadOnly(true);
        List<Integer> integerList = getResponse.getItems();
        ComboBox<Integer> integerComboBox = new ComboBox<>();
        integerComboBox.setItems(integerList);

        applicationDtoBinder.forField(integerComboBox).bind(ApplicationDto::getVkGroupId, ApplicationDto::setVkGroupId);
        applicationDtoBinder.forField(name).bind(ApplicationDto::getName, ApplicationDto::setName);

        applicationDtoBinder.readBean(applicationDto);


//        Button disableAuthorizationButton = new Button("Выход");
//        disableAuthorizationButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        disableAuthorizationButton.setDisableOnClick(false);
//        disableAuthorizationButton.addClickListener(e -> {
//            build();
//        });

        Button updateApp = new Button("Обновить данные перед выгрузкой");
        updateApp.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        updateApp.setDisableOnClick(false);
        updateApp.addClickListener(e -> {
            try {
                applicationDtoBinder.writeBean(applicationDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            applicationService.update(applicationDto);
        });

        Button unloadProducts = new Button("Выгрузить сейчас");
        unloadProducts.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        unloadProducts.setDisableOnClick(false);
        unloadProducts.addClickListener(e -> {

            int ownerId = applicationDto.getVkGroupId();

            try {
                com.vk.api.sdk.objects.market.responses.GetResponse getResponse1 =
                        vk.market().get(actor, -ownerId).execute();

                List<MarketItem> list = getResponse1.getItems();

                for (MarketItem marketItem : list) {
                    vk.market().delete(actor, -ownerId, marketItem.getId()).execute();
                }

            } catch (ApiException | ClientException ex) {
                throw new RuntimeException(ex);
            }

            List<ProductDto> productDtoList = productService.getAll();

            for (ProductDto productDto : productDtoList) {
                try {

                    File file = new File("src/main/resources/static/icons/iconForProducts.jpg");

                    GetMarketUploadServerResponse serverResponse =
                            vk.photos().getMarketUploadServer(actor, applicationDto.getVkGroupId()).execute();

                    MarketUploadResponse uploadResponse = vk.upload()
                            .photoMarket(serverResponse.getUploadUrl().toString(), file).execute();

                    List<SaveMarketPhotoResponse> photoList = vk.photos()
                            .saveMarketPhoto(actor, uploadResponse.getPhoto(),
                                    uploadResponse.getServer(), uploadResponse.getHash())
                            .groupId(applicationDto.getVkGroupId())
                            .cropHash(uploadResponse.getCropHash())
                            .cropData(uploadResponse.getCropData())
                            .execute();

                    SaveMarketPhotoResponse photo = photoList.get(0);

                    try {
                        vk.market().add(actor, -ownerId, productDto.getName(),
                                        productDto.getDescription(), 1)
                                .price(productDto.getPurchasePrice()).mainPhotoId(photo.getId()).execute();
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                } catch (ApiException | ClientException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });


        FormLayout content = new FormLayout();
        content.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0px", 1));
        content.addFormItem(developer, "Вход выполнен:");
//        content.add(disableAuthorizationButton);
        content.addFormItem(name, "Наименование магазина");
        content.addFormItem(integerComboBox, "ID Сообщества ВКонтакте");
        content.add(updateApp);
        content.add(unloadProducts);
        content.setSizeUndefined();

        verticalLayout.add(content);
        return verticalLayout;
    }

//    private UserAuthResponse authResponse;

//    private String code;

    private HorizontalLayout enableAuthorization(ApplicationDto applicationDto) {

        Binder<ApplicationDto> applicationDtoBinder = new Binder<>(ApplicationDto.class);

        HorizontalLayout layoutForButtons = new HorizontalLayout();

        TextField vkUserId = new TextField();
        TextField vkAccessToken = new TextField();

        applicationDtoBinder.forField(vkUserId)
                .asRequired()
                .withValidator(id -> id.length() >= 5, "Слишком короткий id")
                .withConverter(new StringToIntegerConverter("Слишком длинный id"))
                .bind(ApplicationDto::getVkUserId, ApplicationDto::setVkUserId);

        applicationDtoBinder.forField(vkAccessToken)
                .asRequired()
                .withValidator(token -> token.length() >= 5, "Слишком короткий token")
                .bind(ApplicationDto::getVkAccessToken, ApplicationDto::setVkAccessToken);

        applicationDto.setName("Магазин Вконтакте");
        applicationDto.setDescription("ВКонтакте новый");

//        applicationDtoBinder.readBean(applicationDto);

        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(vkUserId, "Ваш id в цифрах:");
        formLayout.addFormItem(vkAccessToken, "Полученный access token:");

        TextArea textArea = new TextArea("Access token по этой ссылке");
        textArea.setValue("oauth.vk.com/authorize?client_id=51422705&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=groups,market,photos,offline&response_type=token&v=5.131");
        textArea.setReadOnly(true);

        Button enableAuthorizationButton = new Button("Подключить магазин ВКонтакте");
        enableAuthorizationButton.addClickListener(e -> {
//
//            UI.getCurrent().navigate("");
//
//            TransportClient transportClient = new HttpTransportClient();
//            VkApiClient vk = new VkApiClient(transportClient);
//
//            try {
//                authResponse = vk.oAuth()
//                        .userAuthorizationCodeFlow(51422705, "jvWdfng6tmQ9end1KM12",
//                                "https://oauth.vk.com/blank.html", code)
//                        .execute();
//            } catch (ApiException | ClientException ex) {
//                throw new RuntimeException(ex);
//            }

//            actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

//            applicationDto.setIsFree(true);

            try {
                applicationDtoBinder.writeBean(applicationDto);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }

            applicationService.create(applicationDto);
            removeAll();
            parent.showButtonEndGrid(false);
        });

        layoutForButtons.add(formLayout, textArea, enableAuthorizationButton);
        return layoutForButtons;
    }

}
