package es.zarca.covellog.domain.model.stock;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.OneToOneMapping;

/**
 *
 * @author francisco
 */
public class StockHistoricoActivoCustomizer implements DescriptorCustomizer {
 
    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        /*customizeDetalleStock(descriptor, "detallePropietario"); 
        customizeDetalleStock(descriptor, "detalleCondicion"); 
        customizeDetalleStock(descriptor, "detalleEstado");
        customizeDetalleStock(descriptor, "detalleFlota");
        customizeDetalleStock(descriptor, "detalleLote");
        customizeDetalleStock(descriptor, "detalleTipoProducto");
        customizeDetalleStock(descriptor, "detalleNumeroSerie");
        customizeDetalleStock(descriptor, "detalleUbicacion");
        customizeDetalleStock(descriptor, "detalleContrato");*/
    }
    
    private void customizeDetalleStock(ClassDescriptor descriptor, String relation) throws Exception {
        OneToOneMapping mapping = (OneToOneMapping) descriptor.getMappingForAttributeName(relation); 
        if (mapping != null) {
            if (mapping.getReferenceClass() != null) {
                ExpressionBuilder eb = new ExpressionBuilder(mapping.getReferenceClass());
                Expression fkExp = eb.getField("stock_id").equal(eb.getParameter("stock.id"));
                if (fkExp != null) {
                    Expression filterExp = eb.get("fechaFin").isNull();
                    mapping.setSelectionCriteria(fkExp.and(filterExp));
                }
            }
        }
    }
}