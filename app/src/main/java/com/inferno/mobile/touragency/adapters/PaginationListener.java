package com.inferno.mobile.touragency.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager layoutManager;
    private LoadMore loadMore;
    private boolean isLastPage;
    private boolean isLoading;
    private int currentPage;


    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) return;
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 15) {
                if (loadMore != null)
                    loadMore.loadMore(currentPage);
            }
        }
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
