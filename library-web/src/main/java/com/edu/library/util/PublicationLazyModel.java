package com.edu.library.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.edu.library.IPublicationService;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Publication;

/**
 * Inner class extend LazyDataModel for pagination
 *
 * @author sipost
 *
 */
@RequestScoped
public class PublicationLazyModel extends LazyDataModel<Publication> {
	private static final long serialVersionUID = -7040989400223372462L;
	private List<Publication> data = new ArrayList<>();
	private final String function;
	private String title;
	private PublicationFilter filter;
	private final Logger logger = Logger.getLogger(PublicationLazyModel.class);

	private final IPublicationService publicationService;

	public PublicationLazyModel(final IPublicationService publicationService) {
		this.publicationService = publicationService;
		this.function = "all";
	}

	public PublicationLazyModel(final String title, final IPublicationService publicationService) {
		this.publicationService = publicationService;
		this.title = title;
		this.function = "search";
	}

	public PublicationLazyModel(final PublicationFilter filter, final IPublicationService publicationService) {
		this.publicationService = publicationService;
		this.function = "filter";
		this.filter = filter;
	}

	@Override
	public Publication getRowData(final String rowKey) {
		for (Publication Publication : this.data) {
			if (Publication.getUuid().equals(rowKey))
				return Publication;
		}
		return null;
	}

	@Override
	public Object getRowKey(final Publication Publication) {
		return Publication.getUuid();
	}

	@Override
	public List<Publication> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {
		this.data.clear();
		switch (this.function) {
		case "search":
			search(first, pageSize);
			break;
		case "filter":
			filter(first, pageSize);
			break;
		default:
			all(first, pageSize);
			break;
		}
		return this.data;
	}

	private void search(final int first, final int pageSize) {
		try {
			int dataSize = (int) (this.publicationService.countSearch(this.title));
			this.setRowCount(dataSize);
			this.data = this.publicationService.searchContent(this.title, first, pageSize);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	private void filter(final int first, final int pageSize) {
		try {
			int dataSize = (int) (this.publicationService.countFilter(this.filter));
			this.setRowCount(dataSize);
			this.data = this.publicationService.filterPublication(this.filter, first, pageSize);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	private void all(final int first, final int pageSize) {
		try {
			int dataSize = (int) (this.publicationService.countAll());
			this.setRowCount(dataSize);
			this.data = this.publicationService.getAll(first, pageSize);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}
}
