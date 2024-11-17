package vn.ptithcm.shopapp.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public class PaginationUtil {

    public static <T> PaginationResponseDTO handlePaginate(Pageable pageable, Page<T> page) {

        PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
        PaginationResponseDTO.Meta meta = new PaginationResponseDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        meta.setTotalOfCurrentPage(page.getNumberOfElements());

        paginationResponseDTO.setMeta(meta);

        return paginationResponseDTO;
    }
}
