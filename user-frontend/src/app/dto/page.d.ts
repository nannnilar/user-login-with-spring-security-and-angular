export interface Page<T> {
  content: T[];         // The actual items on the current page
  pageable: {
    pageNumber: number; // The current page number
    pageSize: number;   // The maximum number of items per page
  };
  totalPages: number;    // The total number of pages
  totalElements: number; // The total number of items across all pages
  last: boolean;         // Whether this is the last page
  first: boolean;        // Whether this is the first page
  empty: boolean;        // Whether the page is empty
}
