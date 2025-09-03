package com.example.project.core.supplier.factory;

import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import com.example.project.core.supplier.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierFactoryImpl implements SupplierFactory {
    @Override
    public Supplier create(SupplierParams params) {
        EntityName name = EntityName.valueOf(params.getName());
        EntityDocument document = EntityDocument.valueOf(params.getDocument());
        Email email = Email.valueOf(params.getEmail());
        Phone phone = Phone.valueOf(params.getPhone());

        return new Supplier(params.getId(), name, document, email, phone);
    }
}
