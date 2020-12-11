import random
from dataclasses import dataclass
from typing import NamedTuple, FrozenSet, List


class Entry(NamedTuple):
    word: str
    letters: FrozenSet[str]


@dataclass
class Game:
    central: str
    others: str
    matching: List[str]

    def __post_init__(self):
        assert len(self.central) == 1
        assert len(self.others) == 6
        assert len(set(self.others)) == 6

    @classmethod
    def generate(cls):
        panagram = random.choice(panagrams)
        central = random.choice(list(panagram.letters))

        matching = {
            entry
            for entry in words
            if entry.letters <= panagram.letters
            and central in entry.letters
            and len(entry.word) >= 4
        }
        assert panagram in matching
        all_panagrams = {word for word in matching if len(set(word)) == 7}
        matching_list = sorted(all_panagrams) + sorted(matching - all_panagrams)

        return cls(
            central, list(panagram.letters - {central}), [w.word for w in matching_list]
        )


def load_words():
    with open("words") as f:
        words = [word.strip() for word in f]

    return [Entry(word, frozenset(word)) for word in words]


words = load_words()
panagrams = [entry for entry in words if len(entry.letters) == 7]
