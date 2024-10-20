package com.fresh.coding.sooatelapi.services.purchases;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.purchases.PurchaseDTO;
import com.fresh.coding.sooatelapi.dtos.searchs.PurchaseSearch;

import java.util.List;

public interface PurchaseService {
    Paginate<List<PurchaseDTO>> findAllPurchases(PurchaseSearch purchaseSearch, int page, int size);
}
