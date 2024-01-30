
namespace Sqwads.Domain.Common;

public abstract class Entity : IEntity
{
    public int Id { get; private set; }

    public string CreatedBy { get; private set; } = null!;

    public DateTime CreatedAt { get; private set; }

    public string? LastModifiedBy { get; private set; }

    public DateTime? LastModifiedAt { get; private set; }

}
