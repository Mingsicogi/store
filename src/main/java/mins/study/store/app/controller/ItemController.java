package mins.study.store.app.controller;

import lombok.RequiredArgsConstructor;
import mins.study.store.app.domain.item.Book;
import mins.study.store.app.domain.item.Item;
import mins.study.store.app.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setIsbn(item.getIsbn());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    @Transactional
    public String updateItem(BookForm form) {
        //        Book book = new Book();
        //
        //        book.setIsbn(form.getIsbn());
        //        book.setAuthor(form.getAuthor());
        //        book.setStockQuantity(form.getStockQuantity());
        //        book.setPrice(form.getPrice());
        //        book.setName(form.getName());
        //        book.setId(form.getId());
        //
        //        itemService.saveItem(book);

        // merge 를 통한 업데이트에서 변경감지를 통한 업데이트로 기능 수정
        Book book = (Book) itemService.findOne(form.getId());
        book.updateBook(form.getAuthor(), form.getIsbn(), form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
